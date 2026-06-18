package com.movie.database.controller;

import com.movie.database.common.Result;
import com.movie.database.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "影人")
@RestController
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    @Operation(summary = "影人详情")
    @GetMapping("/person/{id}")
    public Result<?> detail(@PathVariable Long id) { return Result.ok(personService.detail(id)); }

    @Operation(summary = "影人列表")
    @GetMapping("/person/page")
    public Result<?> page(@RequestParam(defaultValue = "1") int page,
                          @RequestParam(defaultValue = "12") int size,
                          @RequestParam(required = false) String keyword,
                          @RequestParam(required = false) String type) {
        return Result.ok(personService.page(page, size, keyword, type));
    }
}
