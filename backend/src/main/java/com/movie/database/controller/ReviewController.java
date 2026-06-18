package com.movie.database.controller;

import com.movie.database.common.Result;
import com.movie.database.dto.ReviewDto;
import com.movie.database.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "评论")
@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(summary = "评论列表")
    @GetMapping("/review/page")
    public Result<?> page(@RequestParam Long movieId,
                          @RequestParam(defaultValue = "1") int page,
                          @RequestParam(defaultValue = "10") int size) {
        return Result.ok(reviewService.page(movieId, page, size));
    }

    @Operation(summary = "提交评论")
    @PostMapping("/review")
    public Result<?> submit(@Valid @RequestBody ReviewDto dto, Authentication auth) {
        Long userId = (Long) auth.getPrincipal();
        reviewService.submit(userId, dto);
        return Result.ok("评论成功");
    }

    @Operation(summary = "删除评论")
    @DeleteMapping("/review/{id}")
    public Result<?> delete(@PathVariable Long id, Authentication auth) {
        Long userId = (Long) auth.getPrincipal();
        reviewService.delete(userId, id);
        return Result.ok("删除成功");
    }

    @Operation(summary = "我的评论")
    @GetMapping("/review/my")
    public Result<?> myReviews(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "20") int size,
                               Authentication auth) {
        Long userId = (Long) auth.getPrincipal();
        return Result.ok(reviewService.userReviews(userId, page, size));
    }
}
