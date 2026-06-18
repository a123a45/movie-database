package com.movie.database.dto;

import lombok.Data;

@Data
public class CastDto {
    private Long personId;
    private String roleType;
    private String roleName;
}
