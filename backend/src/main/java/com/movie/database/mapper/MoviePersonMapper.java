package com.movie.database.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.movie.database.entity.MoviePerson;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MoviePersonMapper extends BaseMapper<MoviePerson> {
}
