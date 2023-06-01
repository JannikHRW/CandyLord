package com.tutego.date4u.interfaces.rest;

import org.springframework.web.bind.annotation.GetMapping;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

record LastSeenStatistics(
        List<Data> data
) {
    record Data(
            YearMonth yearMonth,
            int count
    ) { }

    @GetMapping(path = "/api/stat/last-seen"
            //, produces = MediaType.APPLICATION_JSON_VALUE
    )
    public LastSeenStatistics lastSeenStatistics() {
        return new LastSeenStatistics(
                Arrays.asList(new LastSeenStatistics.Data(YearMonth.now(), 100)));
    }

    public LastSeenStatistics(List<Data> data) {
        this.data = data;
    }
}