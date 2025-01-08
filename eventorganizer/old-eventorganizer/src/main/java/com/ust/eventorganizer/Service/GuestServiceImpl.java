package com.ust.eventorganizer.Service;

import com.ust.eventorganizer.Repository.GuestRepository;
import com.ust.eventorganizer.exception.EventNotFoundException;
import com.ust.eventorganizer.exception.GuestNotFoundException;
import com.ust.eventorganizer.model.Event;
import com.ust.eventorganizer.model.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestServiceImpl implements GuestService
{
    @Autowired
    private GuestRepository guestRepo;

    @Autowired
    private EventService eventService;

    @Override
    public Guest saveGuest(Guest guest)
    {
        return guestRepo.save(guest);
    }

    @Override
    public List<Guest> getAllGuests() {
        return guestRepo.findAll();
    }

    @Override
    public Guest getGuestById(Long id) throws GuestNotFoundException
    {
        return guestRepo.findById(id).orElseThrow(()->new GuestNotFoundException("Guest with ID: "+id+" is not found"));
    }

    @Override
    public void deleteGuest(Long id) {
        guestRepo.deleteById(id);
    }



    public Guest updateGuest(Long id, Guest guest) throws GuestNotFoundException {
        if(!guestRepo.existsById(id))
        {
            throw new GuestNotFoundException("Guest with ID: "+id+" not found");
        }

        Guest guestToUpdate = getGuestById(id);
        if(guest.getName()!=null)
        {
            guestToUpdate.setName(guest.getName());
        }
        if(guest.getEmail()!=null)
        {
            guestToUpdate.setEmail(guest.getEmail());
        }
        if(guest.getRsvpStatus()!=null)
        {
            guestToUpdate.setRsvpStatus(guest.getRsvpStatus());
        }
        return guestRepo.save(guestToUpdate);

    }

    @Override
    public List<Guest> getAllGuestsForEvent(Long eventId) throws EventNotFoundException {
        Event event = eventService.getEventById(eventId);
        return guestRepo.findAllByEvent(event);
    }

}
