package com.ust.eventorganizer.Repository;

import com.ust.eventorganizer.model.Event;
import com.ust.eventorganizer.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestRepository extends JpaRepository<Guest,Long>
{
    List<Guest> findAllByEvent(Event event);
}
