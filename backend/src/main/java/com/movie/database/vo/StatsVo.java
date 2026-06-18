package com.movie.database.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatsVo {
    private Long movieCount;
    private Long userCount;
    private Long reviewCount;
    private Long totalViews;
}
