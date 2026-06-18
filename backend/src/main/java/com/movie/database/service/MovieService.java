package com.movie.database.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.movie.database.dto.MovieQueryDto;
import com.movie.database.entity.*;
import com.movie.database.mapper.*;
import com.movie.database.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieMapper movieMapper;
    private final MovieGenreMapper movieGenreMapper;
    private final MoviePersonMapper moviePersonMapper;
    private final GenreMapper genreMapper;
    private final CountryMapper countryMapper;
    private final PersonMapper personMapper;
    private final ReviewMapper reviewMapper;
    private final UserMapper userMapper;

    public Page<MovieCardVo> page(MovieQueryDto dto) {
        LambdaQueryWrapper<Movie> qw = new LambdaQueryWrapper<>();
        qw.eq(Movie::getStatus, 1);
        if (dto.getGenreId() != null) {
            List<Long> movieIds = movieGenreMapper.selectList(
                    new LambdaQueryWrapper<MovieGenre>().eq(MovieGenre::getGenreId, dto.getGenreId())
            ).stream().map(MovieGenre::getMovieId).toList();
            if (movieIds.isEmpty()) return new Page<>();
            qw.in(Movie::getId, movieIds);
        }
        if (dto.getCountryId() != null) qw.eq(Movie::getCountryId, dto.getCountryId());
        if (dto.getYear() != null) qw.eq(Movie::getYear, dto.getYear());
        if (dto.getYearFrom() != null) qw.ge(Movie::getYear, dto.getYearFrom());
        if (dto.getYearTo() != null) qw.le(Movie::getYear, dto.getYearTo());
        if (StrUtil.isNotBlank(dto.getContentType())) qw.eq(Movie::getContentType, dto.getContentType());
        if (StrUtil.isNotBlank(dto.getKeyword())) {
            qw.like(Movie::getTitle, dto.getKeyword());
        }
        if ("year".equals(dto.getSort())) qw.orderByDesc(Movie::getYear);
        else if ("views".equals(dto.getSort())) qw.orderByDesc(Movie::getViewCount);
        else qw.orderByDesc(Movie::getRating);

        Page<Movie> page = movieMapper.selectPage(
                new Page<>(dto.getPage(), dto.getSize()), qw
        );
        List<MovieCardVo> records = page.getRecords().stream()
                .map(this::toCardVo).collect(Collectors.toList());
        Page<MovieCardVo> result = new Page<>();
        result.setCurrent(page.getCurrent()).setSize(page.getSize())
                .setTotal(page.getTotal()).setPages(page.getPages()).setRecords(records);
        return result;
    }

    public MovieVo detail(Long id) {
        Movie movie = movieMapper.selectById(id);
        if (movie == null || movie.getStatus() == 0) return null;
        movie.setViewCount(movie.getViewCount() + 1);
        movieMapper.updateById(movie);

        List<Long> genreIds = movieGenreMapper.selectList(
                new LambdaQueryWrapper<MovieGenre>().eq(MovieGenre::getMovieId, id)
        ).stream().map(MovieGenre::getGenreId).toList();
        List<String> genres = genreIds.isEmpty() ? Collections.emptyList()
                : genreMapper.selectBatchIds(genreIds).stream()
                .map(Genre::getName).collect(Collectors.toList());
        Country country = countryMapper.selectById(movie.getCountryId());

        List<MoviePerson> mps = moviePersonMapper.selectList(
                new LambdaQueryWrapper<MoviePerson>().eq(MoviePerson::getMovieId, id).orderByAsc(MoviePerson::getSort)
        );
        List<CastVo> directors = new ArrayList<>();
        List<CastVo> actors = new ArrayList<>();
        for (MoviePerson mp : mps) {
            Person p = personMapper.selectById(mp.getPersonId());
            if (p == null) continue;
            CastVo cv = new CastVo();
            cv.setPersonId(p.getId());
            cv.setName(p.getName());
            cv.setAvatar(p.getAvatar());
            cv.setRoleName(mp.getRoleName());
            if ("DIRECTOR".equals(mp.getRoleType())) directors.add(cv);
            else actors.add(cv);
        }

        List<Review> reviews = reviewMapper.selectList(
                new LambdaQueryWrapper<Review>()
                        .eq(Review::getMovieId, id)
                        .eq(Review::getStatus, 1)
                        .orderByDesc(Review::getCreateTime)
                        .last("LIMIT 5")
        );
        List<ReviewVo> reviewVos = reviews.stream().map(r -> {
            User u = userMapper.selectById(r.getUserId());
            return ReviewVo.builder()
                    .id(r.getId()).userId(r.getUserId())
                    .username(u != null ? u.getNickname() : "").userAvatar(u != null ? u.getAvatar() : "")
                    .rating(r.getRating()).title(r.getTitle()).content(r.getContent())
                    .likeCount(r.getLikeCount()).createTime(r.getCreateTime()).build();
        }).collect(Collectors.toList());

        return MovieVo.builder()
                .id(movie.getId()).title(movie.getTitle()).titleEn(movie.getTitleEn())
                .year(movie.getYear()).duration(movie.getDuration())
                .countryName(country != null ? country.getName() : "").language(movie.getLanguage())
                .releaseDate(movie.getReleaseDate() != null ? movie.getReleaseDate().toString() : "")
                .posterUrl(movie.getPosterUrl()).synopsis(movie.getSynopsis())
                .rating(movie.getRating()).ratingCount(movie.getRatingCount())
                .boxOffice(movie.getBoxOffice()).budget(movie.getBudget())
                .contentType(movie.getContentType()).viewCount(movie.getViewCount())
                .genres(genres).directors(directors).actors(actors).recentReviews(reviewVos)
                .build();
    }

    public Page<MovieCardVo> hot() {
        MovieQueryDto dto = new MovieQueryDto();
        dto.setSort("views");
        dto.setSize(12);
        return page(dto);
    }

    public Page<MovieCardVo> newMovies() {
        MovieQueryDto dto = new MovieQueryDto();
        dto.setSort("year");
        dto.setSize(12);
        return page(dto);
    }

    public Page<MovieCardVo> top250() {
        MovieQueryDto dto = new MovieQueryDto();
        dto.setSort("rating");
        dto.setSize(30);
        return page(dto);
    }

    public List<MovieCardVo> search(String keyword) {
        if (StrUtil.isBlank(keyword)) return Collections.emptyList();
        List<Movie> movies = movieMapper.searchFullText(keyword);
        return movies.stream().map(this::toCardVo).collect(Collectors.toList());
    }

    private MovieCardVo toCardVo(Movie m) {
        List<Long> genreIds = movieGenreMapper.selectList(
                new LambdaQueryWrapper<MovieGenre>().eq(MovieGenre::getMovieId, m.getId())
        ).stream().map(MovieGenre::getGenreId).toList();
        List<String> genreNames = genreIds.isEmpty() ? Collections.emptyList()
                : genreMapper.selectBatchIds(genreIds).stream()
                .map(Genre::getName).collect(Collectors.toList());
        return MovieCardVo.builder()
                .id(m.getId()).title(m.getTitle()).year(m.getYear())
                .posterUrl(m.getPosterUrl()).rating(m.getRating()).ratingCount(m.getRatingCount())
                .contentType(m.getContentType()).duration(m.getDuration())
                .genres(genreNames).build();
    }
}
