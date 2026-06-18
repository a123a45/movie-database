package com.movie.database.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ReviewDto {
    @NotNull(message = "电影ID不能为空")
    private Long movieId;
    @NotNull(message = "评分不能为空")
    private BigDecimal rating;
    private String title;
    @NotBlank(message = "评论内容不能为空")
    private String content;
}
