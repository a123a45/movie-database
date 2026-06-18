package com.movie.database.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.movie.database.entity.Movie;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface MovieMapper extends BaseMapper<Movie> {

    @Select("SELECT * FROM m_movie WHERE status = 1 AND MATCH(title, synopsis) AGAINST(#{keyword} IN NATURAL LANGUAGE MODE)")
    List<Movie> searchFullText(@Param("keyword") String keyword);
}
