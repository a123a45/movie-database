package com.movie.database.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.movie.database.entity.Movie;
import com.movie.database.entity.MoviePerson;
import com.movie.database.entity.Person;
import com.movie.database.mapper.MovieMapper;
import com.movie.database.mapper.MoviePersonMapper;
import com.movie.database.mapper.PersonMapper;
import com.movie.database.vo.PersonFilmographyVo;
import com.movie.database.vo.PersonVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonMapper personMapper;
    private final MoviePersonMapper moviePersonMapper;
    private final MovieMapper movieMapper;

    public PersonVo detail(Long id) {
        Person p = personMapper.selectById(id);
        if (p == null) return null;

        List<MoviePerson> mps = moviePersonMapper.selectList(
                new LambdaQueryWrapper<MoviePerson>().eq(MoviePerson::getPersonId, id).orderByAsc(MoviePerson::getSort)
        );
        List<PersonFilmographyVo> films = new ArrayList<>();
        for (MoviePerson mp : mps) {
            Movie m = movieMapper.selectById(mp.getMovieId());
            if (m == null || m.getStatus() == 0) continue;
            PersonFilmographyVo f = new PersonFilmographyVo();
            f.setMovieId(m.getId());
            f.setMovieTitle(m.getTitle());
            f.setPosterUrl(m.getPosterUrl());
            f.setYear(m.getYear());
            f.setRoleType(mp.getRoleType());
            f.setRoleName(mp.getRoleName());
            films.add(f);
        }

        return PersonVo.builder()
                .id(p.getId()).name(p.getName()).nameEn(p.getNameEn())
                .avatar(p.getAvatar()).gender(p.getGender())
                .birthDate(p.getBirthDate()).nationality(p.getNationality())
                .biography(p.getBiography()).type(p.getType())
                .films(films).build();
    }

    public Page<PersonVo> page(int page, int size, String keyword, String type) {
        LambdaQueryWrapper<Person> qw = new LambdaQueryWrapper<>();
        if (keyword != null && !keyword.isBlank()) qw.like(Person::getName, keyword);
        if (type != null && !type.isBlank()) qw.eq(Person::getType, type);
        qw.orderByAsc(Person::getName);
        Page<Person> p = personMapper.selectPage(new Page<>(page, size), qw);
        List<PersonVo> records = p.getRecords().stream().map(per -> {
            List<MoviePerson> mps = moviePersonMapper.selectList(
                    new LambdaQueryWrapper<MoviePerson>().eq(MoviePerson::getPersonId, per.getId())
            );
            List<PersonFilmographyVo> films = mps.stream().map(mp -> {
                Movie m = movieMapper.selectById(mp.getMovieId());
                if (m == null) return null;
                PersonFilmographyVo f = new PersonFilmographyVo();
                f.setMovieId(m.getId());
                f.setMovieTitle(m.getTitle());
                f.setPosterUrl(m.getPosterUrl());
                f.setYear(m.getYear());
                f.setRoleType(mp.getRoleType());
                f.setRoleName(mp.getRoleName());
                return f;
            }).filter(f -> f != null).collect(Collectors.toList());
            return PersonVo.builder()
                    .id(per.getId()).name(per.getName()).nameEn(per.getNameEn())
                    .avatar(per.getAvatar()).gender(per.getGender())
                    .birthDate(per.getBirthDate()).nationality(per.getNationality())
                    .biography(per.getBiography()).type(per.getType()).films(films).build();
        }).collect(Collectors.toList());
        Page<PersonVo> result = new Page<>();
        result.setCurrent(p.getCurrent()).setSize(p.getSize())
                .setTotal(p.getTotal()).setPages(p.getPages()).setRecords(records);
        return result;
    }
}
