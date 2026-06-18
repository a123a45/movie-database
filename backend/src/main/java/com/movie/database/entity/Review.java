package com.movie.database.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("m_review")
public class Review {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long movieId;
    private Long userId;
    private BigDecimal rating;
    private String title;
    private String content;
    private Integer likeCount;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
