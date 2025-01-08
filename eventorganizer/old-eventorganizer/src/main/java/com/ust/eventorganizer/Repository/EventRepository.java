package com.ust.eventorganizer.Repository;

import com.ust.eventorganizer.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event,Long>
{
    List<Event> findByTitleContainingIgnoreCase(String title); //Event search
    List<Event> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
