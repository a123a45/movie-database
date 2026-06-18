package com.movie.database.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.movie.database.entity.Genre;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GenreMapper extends BaseMapper<Genre> {
}
