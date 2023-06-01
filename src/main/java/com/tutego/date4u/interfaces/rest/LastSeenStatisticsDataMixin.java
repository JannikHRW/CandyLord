package com.tutego.date4u.interfaces.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.jackson.JsonMixin;
import java.time.YearMonth;

@JsonMixin(LastSeenStatistics.Data.class)
interface LastSeenStatisticsDataMixin {
    @JsonProperty("x") YearMonth yearMonth();
    @JsonProperty("y") int count();

    //@JsonCreator
    //public LastSeenStatisticsMixin(@JsonProperty("x") String yearMonth, @JsonProperty("y") String count) {}
}
