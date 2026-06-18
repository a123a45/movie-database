package com.movie.database.vo;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class MovieCardVo {
    private Long id;
    private String title;
    private Integer year;
    private String posterUrl;
    private BigDecimal rating;
    private Integer ratingCount;
    private String contentType;
    private Integer duration;
    private List<String> genres;
}
