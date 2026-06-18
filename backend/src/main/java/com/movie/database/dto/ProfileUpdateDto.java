package com.movie.database.dto;

import lombok.Data;

@Data
public class ProfileUpdateDto {
    private String nickname;
    private String email;
    private String avatar;
}
