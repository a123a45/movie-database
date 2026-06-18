package com.movie.database.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("m_watchlist")
public class Watchlist {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long movieId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
