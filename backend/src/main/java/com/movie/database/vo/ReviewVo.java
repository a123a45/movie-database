package com.movie.database.vo;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ReviewVo {
    private Long id;
    private Long userId;
    private String username;
    private String userAvatar;
    private BigDecimal rating;
    private String title;
    private String content;
    private Integer likeCount;
    private LocalDateTime createTime;
}
