package com.guestManagement.service;

import com.guestManagement.model.Guest;

import java.util.List;
import java.util.Optional;

public interface GuestService
{
    Guest create(Guest guest); // Create a new guest
    Optional<Guest> getGuestById(Long id); // Get guest by ID
    List<Guest> getAll(); // Get all guests
    Optional<Guest> update(Long id, Guest guest); // Update guest details
    void delete(Long id); // Delete guest by ID

//    Optional<Guest> findGuestByEventId(Long eventId); // Find a guest by event ID
//    List<Guest> findByEventId(Long eventId); // Find all guests for a particular event
//    List<Guest> findGuestsByDietaryPreference(String dietaryPreference);
}
