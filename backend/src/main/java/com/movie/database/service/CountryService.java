package com.movie.database.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.movie.database.entity.Country;
import com.movie.database.mapper.CountryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryMapper countryMapper;

    public List<Country> list() {
        return countryMapper.selectList(
                new LambdaQueryWrapper<Country>().orderByAsc(Country::getSort)
        );
    }

    public Country getById(Long id) {
        return countryMapper.selectById(id);
    }
}
