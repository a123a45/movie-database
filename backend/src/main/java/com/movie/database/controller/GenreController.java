package com.movie.database.controller;

import com.movie.database.common.Result;
import com.movie.database.service.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "类型")
@RestController
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @Operation(summary = "所有类型")
    @GetMapping("/genre/list")
    public Result<?> list() { return Result.ok(genreService.list()); }

    @Operation(summary = "类型详情")
    @GetMapping("/genre/{id}")
    public Result<?> detail(@PathVariable Long id) { return Result.ok(genreService.getById(id)); }
}
