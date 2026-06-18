package com.movie.database.controller;

import com.movie.database.common.Result;
import com.movie.database.dto.MovieQueryDto;
import com.movie.database.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "电影", description = "电影浏览/搜索")
@RestController
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @Operation(summary = "分页查询电影列表")
    @GetMapping("/movie/page")
    public Result<?> page(MovieQueryDto dto) {
        return Result.ok(movieService.page(dto));
    }

    @Operation(summary = "电影详情")
    @GetMapping("/movie/{id}")
    public Result<?> detail(@PathVariable Long id) {
        return Result.ok(movieService.detail(id));
    }

    @Operation(summary = "热门电影")
    @GetMapping("/movie/hot")
    public Result<?> hot() {
        return Result.ok(movieService.hot());
    }

    @Operation(summary = "最新电影")
    @GetMapping("/movie/new")
    public Result<?> newMovies() {
        return Result.ok(movieService.newMovies());
    }

    @Operation(summary = "Top250")
    @GetMapping("/movie/top250")
    public Result<?> top250() {
        return Result.ok(movieService.top250());
    }

    @Operation(summary = "搜索电影")
    @GetMapping("/movie/search")
    public Result<?> search(@RequestParam String keyword) {
        return Result.ok(movieService.search(keyword));
    }
}
