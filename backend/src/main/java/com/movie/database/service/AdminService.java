package com.movie.database.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.movie.database.common.BusinessException;
import com.movie.database.dto.MovieDto;
import com.movie.database.entity.*;
import com.movie.database.mapper.*;
import com.movie.database.vo.StatsVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final MovieMapper movieMapper;
    private final MovieGenreMapper movieGenreMapper;
    private final MoviePersonMapper moviePersonMapper;
    private final PersonMapper personMapper;
    private final GenreMapper genreMapper;
    private final UserMapper userMapper;
    private final ReviewMapper reviewMapper;
    private final NewsMapper newsMapper;

    // Stats
    public StatsVo getStats() {
        return StatsVo.builder()
                .movieCount(movieMapper.selectCount(new LambdaQueryWrapper<Movie>().eq(Movie::getStatus, 1)))
                .userCount(userMapper.selectCount(null))
                .reviewCount(reviewMapper.selectCount(null))
                .totalViews(movieMapper.selectList(null).stream().mapToLong(Movie::getViewCount).sum())
                .build();
    }

    // Movie CRUD
    @Transactional
    public void addMovie(MovieDto dto) {
        Movie movie = new Movie();
        movie.setTitle(dto.getTitle());
        movie.setTitleEn(dto.getTitleEn());
        movie.setYear(dto.getYear());
        movie.setDuration(dto.getDuration());
        movie.setCountryId(dto.getCountryId());
        movie.setLanguage(dto.getLanguage());
        if (dto.getReleaseDate() != null) movie.setReleaseDate(LocalDate.parse(dto.getReleaseDate()));
        movie.setPosterUrl(dto.getPosterUrl());
        movie.setSynopsis(dto.getSynopsis());
        movie.setBoxOffice(dto.getBoxOffice());
        movie.setContentType(dto.getContentType() != null ? dto.getContentType() : "MOVIE");
        movie.setStatus(1);
        movieMapper.insert(movie);
        if (dto.getGenreIds() != null) {
            for (Long gid : dto.getGenreIds()) {
                MovieGenre mg = new MovieGenre();
                mg.setMovieId(movie.getId());
                mg.setGenreId(gid);
                movieGenreMapper.insert(mg);
            }
        }
        if (dto.getCast() != null) {
            for (var c : dto.getCast()) {
                MoviePerson mp = new MoviePerson();
                mp.setMovieId(movie.getId());
                mp.setPersonId(c.getPersonId());
                mp.setRoleType(c.getRoleType());
                mp.setRoleName(c.getRoleName());
                moviePersonMapper.insert(mp);
            }
        }
    }

    @Transactional
    public void updateMovie(Long id, MovieDto dto) {
        Movie movie = movieMapper.selectById(id);
        if (movie == null) throw new BusinessException("电影不存在");
        movie.setTitle(dto.getTitle());
        movie.setTitleEn(dto.getTitleEn());
        movie.setYear(dto.getYear());
        movie.setDuration(dto.getDuration());
        movie.setCountryId(dto.getCountryId());
        movie.setLanguage(dto.getLanguage());
        if (dto.getReleaseDate() != null) movie.setReleaseDate(LocalDate.parse(dto.getReleaseDate()));
        movie.setPosterUrl(dto.getPosterUrl());
        movie.setSynopsis(dto.getSynopsis());
        movie.setBoxOffice(dto.getBoxOffice());
        movie.setContentType(dto.getContentType());
        movieMapper.updateById(movie);
        movieGenreMapper.delete(new LambdaQueryWrapper<MovieGenre>().eq(MovieGenre::getMovieId, id));
        if (dto.getGenreIds() != null) {
            for (Long gid : dto.getGenreIds()) {
                MovieGenre mg = new MovieGenre();
                mg.setMovieId(id);
                mg.setGenreId(gid);
                movieGenreMapper.insert(mg);
            }
        }
        moviePersonMapper.delete(new LambdaQueryWrapper<MoviePerson>().eq(MoviePerson::getMovieId, id));
        if (dto.getCast() != null) {
            for (var c : dto.getCast()) {
                MoviePerson mp = new MoviePerson();
                mp.setMovieId(id);
                mp.setPersonId(c.getPersonId());
                mp.setRoleType(c.getRoleType());
                mp.setRoleName(c.getRoleName());
                moviePersonMapper.insert(mp);
            }
        }
    }

    public void deleteMovie(Long id) {
        Movie movie = movieMapper.selectById(id);
        if (movie != null) {
            movie.setStatus(0);
            movieMapper.updateById(movie);
        }
    }

    // Person CRUD
    public void addPerson(Person p) { personMapper.insert(p); }
    public void updatePerson(Person p) { personMapper.updateById(p); }
    public void deletePerson(Long id) { personMapper.deleteById(id); }

    // Genre CRUD
    public void addGenre(Genre g) { genreMapper.insert(g); }
    public void updateGenre(Genre g) { genreMapper.updateById(g); }
    public void deleteGenre(Long id) { genreMapper.deleteById(id); }

    // User management
    public Page<User> getUserPage(int page, int size, String keyword) {
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        if (keyword != null && !keyword.isBlank()) qw.like(User::getUsername, keyword).or().like(User::getNickname, keyword);
        qw.orderByDesc(User::getCreateTime);
        return userMapper.selectPage(new Page<>(page, size), qw);
    }

    public void toggleUserStatus(Long id) {
        User user = userMapper.selectById(id);
        if (user != null) {
            user.setStatus(user.getStatus() == 1 ? 0 : 1);
            userMapper.updateById(user);
        }
    }

    // News CRUD
    public void addNews(News n) { newsMapper.insert(n); }
    public void updateNews(News n) { newsMapper.updateById(n); }
    public void deleteNews(Long id) { newsMapper.deleteById(id); }
}
