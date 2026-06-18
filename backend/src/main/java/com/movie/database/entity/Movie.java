package com.movie.database.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("m_movie")
public class Movie {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String titleEn;
    private Integer year;
    private Integer duration;
    private Long countryId;
    private String language;
    private LocalDate releaseDate;
    private String posterUrl;
    private String trailerUrl;
    private String synopsis;
    private BigDecimal rating;
    private Integer ratingCount;
    private String boxOffice;
    private String budget;
    private String contentType;
    private Integer status;
    private Integer viewCount;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
