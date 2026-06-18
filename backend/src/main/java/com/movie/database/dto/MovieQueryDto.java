package com.movie.database.dto;

import lombok.Data;

@Data
public class MovieQueryDto {
    private Integer page = 1;
    private Integer size = 12;
    private String keyword;
    private Long genreId;
    private Long countryId;
    private Integer year;
    private Integer yearFrom;
    private Integer yearTo;
    private String sort;  // rating, year, views
    private String contentType;
}
