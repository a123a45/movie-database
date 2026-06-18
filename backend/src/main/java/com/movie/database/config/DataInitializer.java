package com.movie.database.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.movie.database.entity.User;
import com.movie.database.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Initialize seed users with properly BCrypt-hashed passwords.
 * The SQL data.sql contains seed user records with placeholder hashes —
 * this runner replaces them with real BCrypt hashes on first launch.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        initUser(1L, "admin", "admin123", "管理员", "admin@cinebase.com", "ADMIN");
        initUser(2L, "user", "user123", "影迷小王", "user@cinebase.com", "USER");
        initUser(3L, "zhangwei", "abc123", "张伟", "zhangwei@email.com", "USER");
        initUser(4L, "liming", "abc123", "李明", "liming@email.com", "USER");
        log.info("Seed users initialized");
    }

    private void initUser(Long id, String username, String rawPassword, String nickname, String email, String role) {
        User existing = userMapper.selectById(id);
        if (existing == null) {
            User user = new User();
            user.setId(id);
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(rawPassword));
            user.setNickname(nickname);
            user.setEmail(email);
            user.setRole(role);
            user.setStatus(1);
            userMapper.insert(user);
        } else {
            // Always update password to ensure correct BCrypt hash
            existing.setPassword(passwordEncoder.encode(rawPassword));
            existing.setNickname(nickname);
            existing.setEmail(email);
            existing.setRole(role);
            existing.setStatus(1);
            userMapper.updateById(existing);
        }
    }
}
