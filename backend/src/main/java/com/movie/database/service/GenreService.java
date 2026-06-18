package com.movie.database.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.movie.database.entity.Genre;
import com.movie.database.mapper.GenreMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreMapper genreMapper;

    public List<Genre> list() {
        return genreMapper.selectList(
                new LambdaQueryWrapper<Genre>().orderByAsc(Genre::getSort)
        );
    }

    public Genre getById(Long id) {
        return genreMapper.selectById(id);
    }
}
