package com.movie.database.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.movie.database.entity.Person;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PersonMapper extends BaseMapper<Person> {
}
