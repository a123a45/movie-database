package com.movie.database.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.movie.database.entity.Watchlist;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WatchlistMapper extends BaseMapper<Watchlist> {
}
