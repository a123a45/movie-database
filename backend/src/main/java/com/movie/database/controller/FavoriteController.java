package com.movie.database.controller;

import com.movie.database.common.Result;
import com.movie.database.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "收藏与想看")
@RestController
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @Operation(summary = "切换收藏")
    @PostMapping("/favorite/toggle/{movieId}")
    public Result<?> toggleFav(@PathVariable Long movieId, Authentication auth) {
        Long userId = (Long) auth.getPrincipal();
        boolean added = favoriteService.toggleFavorite(userId, movieId);
        return Result.ok(added ? "已收藏" : "已取消收藏");
    }

    @Operation(summary = "我的收藏")
    @GetMapping("/favorite/page")
    public Result<?> favPage(@RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "12") int size,
                             Authentication auth) {
        Long userId = (Long) auth.getPrincipal();
        return Result.ok(favoriteService.getFavoritesPage(userId, page, size));
    }

    @Operation(summary = "切换想看")
    @PostMapping("/watchlist/toggle/{movieId}")
    public Result<?> toggleWatch(@PathVariable Long movieId, Authentication auth) {
        Long userId = (Long) auth.getPrincipal();
        boolean added = favoriteService.toggleWatchlist(userId, movieId);
        return Result.ok(added ? "已标记想看" : "已取消想看");
    }

    @Operation(summary = "我的想看")
    @GetMapping("/watchlist/page")
    public Result<?> watchPage(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "12") int size,
                               Authentication auth) {
        Long userId = (Long) auth.getPrincipal();
        return Result.ok(favoriteService.getWatchlistPage(userId, page, size));
    }
}
