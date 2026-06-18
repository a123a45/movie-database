package com.movie.database.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("m_movie_genre")
public class MovieGenre {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long movieId;
    private Long genreId;
}
