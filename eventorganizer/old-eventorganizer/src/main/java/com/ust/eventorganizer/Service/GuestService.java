package com.ust.eventorganizer.Service;

import com.ust.eventorganizer.exception.EventNotFoundException;
import com.ust.eventorganizer.exception.GuestNotFoundException;
import com.ust.eventorganizer.model.Guest;

import java.util.List;

public interface GuestService {
    public Guest saveGuest(Guest guest);
    public List<Guest> getAllGuests();
    public Guest getGuestById(Long id) throws GuestNotFoundException;
    public void deleteGuest(Long id);
    public Guest updateGuest(Long id, Guest guest) throws GuestNotFoundException;

    public List<Guest> getAllGuestsForEvent(Long eventId) throws EventNotFoundException;
}