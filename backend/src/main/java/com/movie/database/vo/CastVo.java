package com.movie.database.vo;

import lombok.Data;

@Data
public class CastVo {
    private Long personId;
    private String name;
    private String avatar;
    private String roleName;
}
