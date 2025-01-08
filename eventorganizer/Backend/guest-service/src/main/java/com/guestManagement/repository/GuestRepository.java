package com.guestManagement.repository;

import com.guestManagement.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest,Long>
{
//    public Optional<Guest> findGuestByEventId(Long eventId);
//    public List<Guest> findByEventId(Long eventId);
//    public List<Guest> findByDietaryPreference(String dietaryPreference);
}
