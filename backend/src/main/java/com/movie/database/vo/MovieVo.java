package com.movie.database.vo;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class MovieVo {
    private Long id;
    private String title;
    private String titleEn;
    private Integer year;
    private Integer duration;
    private String countryName;
    private String language;
    private String releaseDate;
    private String posterUrl;
    private String synopsis;
    private BigDecimal rating;
    private Integer ratingCount;
    private String boxOffice;
    private String budget;
    private String contentType;
    private Integer viewCount;
    private List<String> genres;
    private List<CastVo> directors;
    private List<CastVo> actors;
    private List<ReviewVo> recentReviews;
}
