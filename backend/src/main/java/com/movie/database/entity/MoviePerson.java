package com.movie.database.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("m_movie_person")
public class MoviePerson {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long movieId;
    private Long personId;
    private String roleType;
    private String roleName;
    private Integer sort;
}
