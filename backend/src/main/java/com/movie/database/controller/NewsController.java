package com.movie.database.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.movie.database.common.Result;
import com.movie.database.entity.News;
import com.movie.database.mapper.NewsMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "资讯", description = "影视资讯浏览")
@RestController
@RequiredArgsConstructor
public class NewsController {

    private final NewsMapper newsMapper;

    @Operation(summary = "分页查询资讯列表")
    @GetMapping("/news/page")
    public Result<?> page(@RequestParam(defaultValue = "1") int page,
                          @RequestParam(defaultValue = "10") int size) {
        LambdaQueryWrapper<News> qw = new LambdaQueryWrapper<>();
        qw.eq(News::getStatus, 1);
        qw.orderByDesc(News::getCreateTime);
        Page<News> result = newsMapper.selectPage(new Page<>(page, size), qw);
        return Result.ok(result);
    }

    @Operation(summary = "资讯详情")
    @GetMapping("/news/{id}")
    public Result<?> detail(@PathVariable Long id) {
        News news = newsMapper.selectById(id);
        if (news == null || news.getStatus() == 0) {
            return Result.error("资讯不存在或已下线");
        }
        news.setViewCount(news.getViewCount() != null ? news.getViewCount() + 1 : 1);
        newsMapper.updateById(news);
        return Result.ok(news);
    }
}
