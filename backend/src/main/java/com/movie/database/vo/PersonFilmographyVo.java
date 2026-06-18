package com.movie.database.vo;

import lombok.Data;

@Data
public class PersonFilmographyVo {
    private Long movieId;
    private String movieTitle;
    private String posterUrl;
    private Integer year;
    private String roleType;
    private String roleName;
}
