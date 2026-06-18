package com.movie.database.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("m_person")
public class Person {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String nameEn;
    private String avatar;
    private Integer gender;
    private LocalDate birthDate;
    private String nationality;
    private String biography;
    private String type;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
