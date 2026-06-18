package com.movie.database.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoVo {
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String avatar;
    private String role;
}
