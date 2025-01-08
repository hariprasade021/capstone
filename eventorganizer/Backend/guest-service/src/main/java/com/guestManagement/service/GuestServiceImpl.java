package com.guestManagement.service;

import com.guestManagement.model.Guest;
import com.guestManagement.repository.GuestRepository;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuestServiceImpl implements GuestService
{
    @Autowired
    private GuestRepository guestRepo;

    @Override
    public Guest create(Guest guest) {
        return guestRepo.save(guest);
    }

    @Override
    public Optional<Guest> getGuestById(Long id) {
        return guestRepo.findById(id);
    }

    @Override
    public List<Guest> getAll() {
        // Return a list of all guests from the database.
        return guestRepo.findAll();
    }

    @Override
    public Optional<Guest> update(Long id, Guest guest)
    {
        Optional<Guest> existingGuestOpt = guestRepo.findById(id);
        if (existingGuestOpt.isPresent()) {
            Guest existingGuest = existingGuestOpt.get();
            existingGuest.setName(guest.getName());
            existingGuest.setEmail(guest.getEmail());
            existingGuest.setDiet(guest.getDiet());
            existingGuest.setRsvpStatus(guest.getRsvpStatus());

            return Optional.of(guestRepo.save(existingGuest));
        }
        // If the guest doesn't exist, return an empty Optional.
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        // Delete a guest by their ID.
        guestRepo.deleteById(id);
    }

//    @Override
//    public Optional<Guest> findGuestByEventId(Long eventId) {
//        return guestRepo.findGuestByEventId(eventId);
//    }
//
//    @Override
//    public List<Guest> findByEventId(Long eventId) {
//        return guestRepo.findByEventId(eventId);
//    }
//
//    @Override
//    public List<Guest> findGuestsByDietaryPreference(String dietaryPreference) {
//        return guestRepo.findByDietaryPreference(dietaryPreference);
//    }
}
