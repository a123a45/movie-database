package com.movie.database.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("m_genre")
public class Genre {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String nameEn;
    private Integer sort;
    private String description;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
