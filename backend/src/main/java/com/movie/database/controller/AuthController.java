package com.movie.database.controller;

import com.movie.database.common.Result;
import com.movie.database.dto.LoginDto;
import com.movie.database.dto.PasswordChangeDto;
import com.movie.database.dto.ProfileUpdateDto;
import com.movie.database.dto.RegisterDto;
import com.movie.database.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "认证", description = "登录/注册/用户信息")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "用户登录")
    @PostMapping("/auth/login")
    public Result<?> login(@Valid @RequestBody LoginDto dto) {
        return Result.ok(authService.login(dto));
    }

    @Operation(summary = "用户注册")
    @PostMapping("/auth/register")
    public Result<?> register(@Valid @RequestBody RegisterDto dto) {
        authService.register(dto);
        return Result.ok("注册成功");
    }

    @Operation(summary = "获取当前用户信息")
    @GetMapping("/auth/info")
    public Result<?> getInfo(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return Result.ok(authService.getUserInfo(userId));
    }

    @Operation(summary = "退出登录")
    @PostMapping("/auth/logout")
    public Result<?> logout() {
        return Result.ok("已退出");
    }

    @Operation(summary = "修改个人资料")
    @PutMapping("/user/profile")
    public Result<?> updateProfile(@RequestBody ProfileUpdateDto dto, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        authService.updateProfile(userId, dto);
        return Result.ok("修改成功");
    }

    @Operation(summary = "修改密码")
    @PutMapping("/user/password")
    public Result<?> changePassword(@Valid @RequestBody PasswordChangeDto dto, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        authService.changePassword(userId, dto);
        return Result.ok("密码修改成功");
    }
}
