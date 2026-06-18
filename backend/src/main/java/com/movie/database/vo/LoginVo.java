package com.movie.database.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginVo {
    private String token;
    private UserInfoVo userInfo;
}
