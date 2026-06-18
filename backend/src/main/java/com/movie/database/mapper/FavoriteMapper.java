package com.movie.database.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.movie.database.entity.Favorite;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FavoriteMapper extends BaseMapper<Favorite> {
}
