package com.eventManagement.repository;

import com.eventManagement.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event,Long>
{
    // Find events by name containing a substring, case-insensitive
    List<Event> findByNameContainingIgnoreCase(String name);

    // Find events with startDate within a specified range
    List<Event> findByStartDateBetween(LocalDate startDate, LocalDate endDate);

    List<Event> findByUserId(Long userId);
//
//    List<Event> findByClientId(Long clientId);
}
