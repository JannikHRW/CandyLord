package com.tutego.date4u.core;

import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    @Query( nativeQuery = true, value = """
            SELECT YEAR(p.lastseen) as y, MONTH(p.lastseen) as m, COUNT(*) as count FROM Profile p
            GROUP BY YEAR(p.lastseen), MONTH(p.lastseen)
                        """)
    List<Tuple> findMonthlyProfileCount();


    @Query( value = """
  SELECT YEAR(p.lastseen) AS y, MONTH(p.lastseen) AS m, COUNT(*) AS count
  FROM   Profile p
  WHERE  p.lastseen > :startDate AND p.lastseen < :endDate
  GROUP BY YEAR(p.lastseen), MONTH(p.lastseen)""" )
    List<Tuple> findMonthlyProfileCount( LocalDateTime startDate,
                                               LocalDateTime endDate );




}