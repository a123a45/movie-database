package com.movie.database.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.movie.database.entity.Favorite;
import com.movie.database.entity.Movie;
import com.movie.database.entity.Watchlist;
import com.movie.database.mapper.FavoriteMapper;
import com.movie.database.mapper.MovieMapper;
import com.movie.database.mapper.WatchlistMapper;
import com.movie.database.vo.MovieCardVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteMapper favoriteMapper;
    private final WatchlistMapper watchlistMapper;
    private final MovieMapper movieMapper;
    private final MovieService movieService;

    public boolean toggleFavorite(Long userId, Long movieId) {
        Favorite exist = favoriteMapper.selectOne(
                new LambdaQueryWrapper<Favorite>().eq(Favorite::getUserId, userId).eq(Favorite::getMovieId, movieId)
        );
        if (exist != null) { favoriteMapper.deleteById(exist.getId()); return false; }
        else { Favorite f = new Favorite(); f.setUserId(userId); f.setMovieId(movieId); favoriteMapper.insert(f); return true; }
    }

    public boolean toggleWatchlist(Long userId, Long movieId) {
        Watchlist exist = watchlistMapper.selectOne(
                new LambdaQueryWrapper<Watchlist>().eq(Watchlist::getUserId, userId).eq(Watchlist::getMovieId, movieId)
        );
        if (exist != null) { watchlistMapper.deleteById(exist.getId()); return false; }
        else { Watchlist w = new Watchlist(); w.setUserId(userId); w.setMovieId(movieId); watchlistMapper.insert(w); return true; }
    }

    public List<Long> getUserFavorites(Long userId) {
        return favoriteMapper.selectList(
                new LambdaQueryWrapper<Favorite>().eq(Favorite::getUserId, userId)
        ).stream().map(Favorite::getMovieId).toList();
    }

    public List<Long> getUserWatchlist(Long userId) {
        return watchlistMapper.selectList(
                new LambdaQueryWrapper<Watchlist>().eq(Watchlist::getUserId, userId)
        ).stream().map(Watchlist::getMovieId).toList();
    }

    public IPage<MovieCardVo> getFavoritesPage(Long userId, int page, int size) {
        LambdaQueryWrapper<Favorite> qw = new LambdaQueryWrapper<Favorite>()
                .eq(Favorite::getUserId, userId).orderByDesc(Favorite::getCreateTime);
        Page<Favorite> p = favoriteMapper.selectPage(new Page<>(page, size), qw);
        List<MovieCardVo> records = p.getRecords().stream()
                .map(f -> movieService.detail(f.getMovieId()))
                .filter(v -> v != null)
                .map(v -> MovieCardVo.builder().id(v.getId()).title(v.getTitle())
                        .year(v.getYear()).posterUrl(v.getPosterUrl()).rating(v.getRating())
                        .ratingCount(v.getRatingCount()).contentType(v.getContentType()).genres(v.getGenres()).build())
                .toList();
        Page<MovieCardVo> result = new Page<>();
        return result.setCurrent(p.getCurrent()).setSize(p.getSize())
                .setTotal(p.getTotal()).setPages(p.getPages()).setRecords(records);
    }

    public IPage<MovieCardVo> getWatchlistPage(Long userId, int page, int size) {
        LambdaQueryWrapper<Watchlist> qw = new LambdaQueryWrapper<Watchlist>()
                .eq(Watchlist::getUserId, userId).orderByDesc(Watchlist::getCreateTime);
        Page<Watchlist> p = watchlistMapper.selectPage(new Page<>(page, size), qw);
        List<MovieCardVo> records = p.getRecords().stream()
                .map(w -> movieService.detail(w.getMovieId()))
                .filter(v -> v != null)
                .map(v -> MovieCardVo.builder().id(v.getId()).title(v.getTitle())
                        .year(v.getYear()).posterUrl(v.getPosterUrl()).rating(v.getRating())
                        .ratingCount(v.getRatingCount()).contentType(v.getContentType()).genres(v.getGenres()).build())
                .toList();
        Page<MovieCardVo> result = new Page<>();
        return result.setCurrent(p.getCurrent()).setSize(p.getSize())
                .setTotal(p.getTotal()).setPages(p.getPages()).setRecords(records);
    }
}
