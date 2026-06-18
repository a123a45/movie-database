package com.movie.database.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.movie.database.entity.Review;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewMapper extends BaseMapper<Review> {
}
