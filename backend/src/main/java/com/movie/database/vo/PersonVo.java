package com.movie.database.vo;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class PersonVo {
    private Long id;
    private String name;
    private String nameEn;
    private String avatar;
    private Integer gender;
    private LocalDate birthDate;
    private String nationality;
    private String biography;
    private String type;
    private List<PersonFilmographyVo> films;
}
