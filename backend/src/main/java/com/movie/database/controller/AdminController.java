package com.movie.database.controller;

import com.movie.database.common.Result;
import com.movie.database.dto.MovieDto;
import com.movie.database.entity.*;
import com.movie.database.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "管理后台")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final AdminService adminService;

    @Operation(summary = "仪表盘")
    @GetMapping("/stats")
    public Result<?> stats() { return Result.ok(adminService.getStats()); }

    @Operation(summary = "新增电影")
    @PostMapping("/movie")
    public Result<?> addMovie(@RequestBody MovieDto dto) { adminService.addMovie(dto); return Result.ok("添加成功"); }

    @Operation(summary = "更新电影")
    @PutMapping("/movie")
    public Result<?> updateMovie(@RequestBody MovieDto dto, @RequestParam Long id) { adminService.updateMovie(id, dto); return Result.ok("更新成功"); }

    @Operation(summary = "删除电影")
    @DeleteMapping("/movie/{id}")
    public Result<?> deleteMovie(@PathVariable Long id) { adminService.deleteMovie(id); return Result.ok("删除成功"); }

    @Operation(summary = "新增影人")
    @PostMapping("/person")
    public Result<?> addPerson(@RequestBody Person p) { adminService.addPerson(p); return Result.ok("添加成功"); }

    @Operation(summary = "更新影人")
    @PutMapping("/person")
    public Result<?> updatePerson(@RequestBody Person p) { adminService.updatePerson(p); return Result.ok("更新成功"); }

    @Operation(summary = "删除影人")
    @DeleteMapping("/person/{id}")
    public Result<?> deletePerson(@PathVariable Long id) { adminService.deletePerson(id); return Result.ok("删除成功"); }

    @Operation(summary = "新增类型")
    @PostMapping("/genre")
    public Result<?> addGenre(@RequestBody Genre g) { adminService.addGenre(g); return Result.ok("添加成功"); }

    @Operation(summary = "更新类型")
    @PutMapping("/genre")
    public Result<?> updateGenre(@RequestBody Genre g) { adminService.updateGenre(g); return Result.ok("更新成功"); }

    @Operation(summary = "删除类型")
    @DeleteMapping("/genre/{id}")
    public Result<?> deleteGenre(@PathVariable Long id) { adminService.deleteGenre(id); return Result.ok("删除成功"); }

    @Operation(summary = "用户列表")
    @GetMapping("/user/page")
    public Result<?> userPage(@RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(required = false) String keyword) {
        return Result.ok(adminService.getUserPage(page, size, keyword));
    }

    @Operation(summary = "切换用户状态")
    @PutMapping("/user/status")
    public Result<?> toggleUser(@RequestParam Long id) { adminService.toggleUserStatus(id); return Result.ok("状态已更新"); }

    @Operation(summary = "新增资讯")
    @PostMapping("/news")
    public Result<?> addNews(@RequestBody News n) { adminService.addNews(n); return Result.ok("添加成功"); }

    @Operation(summary = "更新资讯")
    @PutMapping("/news")
    public Result<?> updateNews(@RequestBody News n) { adminService.updateNews(n); return Result.ok("更新成功"); }

    @Operation(summary = "删除资讯")
    @DeleteMapping("/news/{id}")
    public Result<?> deleteNews(@PathVariable Long id) { adminService.deleteNews(id); return Result.ok("删除成功"); }
}
