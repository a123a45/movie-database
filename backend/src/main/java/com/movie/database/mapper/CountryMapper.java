package com.movie.database.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.movie.database.entity.Country;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CountryMapper extends BaseMapper<Country> {
}
