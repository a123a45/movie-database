package com.movie.database.controller;

import com.movie.database.common.Result;
import com.movie.database.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StatsController {
    private final AdminService adminService;

    @Operation(summary = "首页统计")
    @GetMapping("/stats/home")
    public Result<?> homeStats() {
        return Result.ok(adminService.getStats());
    }
}
