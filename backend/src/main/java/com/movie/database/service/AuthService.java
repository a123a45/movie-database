package com.movie.database.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.movie.database.common.BusinessException;
import com.movie.database.config.JwtUtils;
import com.movie.database.dto.LoginDto;
import com.movie.database.dto.PasswordChangeDto;
import com.movie.database.dto.ProfileUpdateDto;
import com.movie.database.dto.RegisterDto;
import com.movie.database.entity.User;
import com.movie.database.mapper.UserMapper;
import com.movie.database.vo.LoginVo;
import com.movie.database.vo.UserInfoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public LoginVo login(LoginDto dto) {
        User user = userMapper.selectOne(
                new LambdaQueryWrapper<User>().eq(User::getUsername, dto.getUsername())
        );
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }
        if (user.getStatus() == 0) {
            throw new BusinessException("账号已被禁用");
        }
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }
        String token = jwtUtils.generateToken(user.getId(), user.getRole());
        return LoginVo.builder()
                .token(token)
                .userInfo(toVo(user))
                .build();
    }

    public void register(RegisterDto dto) {
        Long count = userMapper.selectCount(
                new LambdaQueryWrapper<User>().eq(User::getUsername, dto.getUsername())
        );
        if (count > 0) {
            throw new BusinessException("用户名已存在");
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setNickname(dto.getNickname() != null ? dto.getNickname() : dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setRole("USER");
        user.setStatus(1);
        userMapper.insert(user);
    }

    public UserInfoVo getUserInfo(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        return toVo(user);
    }

    public void changePassword(Long userId, PasswordChangeDto dto) {
        User user = userMapper.selectById(userId);
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new BusinessException("旧密码不正确");
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userMapper.updateById(user);
    }

    public void updateProfile(Long userId, ProfileUpdateDto dto) {
        User user = userMapper.selectById(userId);
        if (dto.getNickname() != null) user.setNickname(dto.getNickname());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getAvatar() != null) user.setAvatar(dto.getAvatar());
        userMapper.updateById(user);
    }

    private UserInfoVo toVo(User user) {
        return UserInfoVo.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .role(user.getRole())
                .build();
    }
}
