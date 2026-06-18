package com.movie.database.controller;

import com.movie.database.common.Result;
import com.movie.database.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "国家")
@RestController
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;

    @Operation(summary = "所有国家")
    @GetMapping("/country/list")
    public Result<?> list() { return Result.ok(countryService.list()); }
}
