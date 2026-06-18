package com.movie.database.dto;

import lombok.Data;
import java.util.List;

@Data
public class MovieDto {
    private String title;
    private String titleEn;
    private Integer year;
    private Integer duration;
    private Long countryId;
    private String language;
    private String releaseDate;
    private String posterUrl;
    private String synopsis;
    private String boxOffice;
    private String contentType;
    private List<Long> genreIds;
    private List<CastDto> cast;
}
