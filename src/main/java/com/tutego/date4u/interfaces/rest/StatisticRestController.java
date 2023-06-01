package com.tutego.date4u.interfaces.rest;

import com.tutego.date4u.core.ProfileRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@RestController
public class StatisticRestController {

    private final ProfileRepository profiles;

    public StatisticRestController( ProfileRepository profiles ) {
        this.profiles = profiles;
    }

    @RequestMapping( "/api/stat/total" )
    public String totalNumberOfRegisteredUnicorns() {
        return String.valueOf( profiles.count() );
    }

    @GetMapping( "/api/stat/last-seen" )
    public LastSeenStatistics lastSeenStatistics(
            @RequestParam( "start" ) Optional<YearMonth>optionalStart,
            @RequestParam( value ="end" ) Optional<YearMonth> optionalEnd
    ) {

        YearMonth start = optionalStart.orElse(YearMonth.now().minusYears( 2 ));
        YearMonth end   = optionalEnd.orElse(YearMonth.now());

        List<LastSeenStatistics.Data> data = profiles.findMonthlyProfileCount(start.atEndOfMonth().atStartOfDay(), end.atEndOfMonth().atStartOfDay()).stream().map(
                tuple -> {
                    return new LastSeenStatistics.Data( YearMonth.of(Integer.parseInt(tuple.get("y").toString()),
                            Integer.parseInt(tuple.get("m").toString())),
                            Integer.parseInt( tuple.get( "count" ).toString() ) );
                } ).toList();

        return new LastSeenStatistics( data );
    }


    @GetMapping( "/api/stat/find-profile" )
    public LastSeenStatistics findProfileCount() {
        List<LastSeenStatistics.Data> data = profiles.findMonthlyProfileCount().stream().map(
                tuple -> {
                    return new LastSeenStatistics.Data( YearMonth.of(Integer.parseInt(tuple.get("y").toString()),
                            Integer.parseInt(tuple.get("m").toString())),
                            Integer.parseInt( tuple.get( "count" ).toString() ) );
                } ).toList();

        return new LastSeenStatistics( data );
    }
}