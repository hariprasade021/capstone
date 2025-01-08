package com.ust.eventorganizer.Controller;


import com.ust.eventorganizer.Service.GuestService;
import com.ust.eventorganizer.exception.GuestNotFoundException;
import com.ust.eventorganizer.model.Event;
import com.ust.eventorganizer.model.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/guests")
public class GuestController
{
    @Autowired
    private GuestService guestService;


    @PostMapping
    public Guest createGuest(@RequestBody Guest guest) {
        return guestService.saveGuest(guest);
    }


    @GetMapping
    public List<Guest> getAllGuests() {
        return guestService.getAllGuests();
    }

    // Get an event by ID
    @GetMapping("/{id}")
    public Guest getGuestById(@PathVariable Long id) throws GuestNotFoundException {
        return guestService.getGuestById(id);
    }


    @PutMapping("/{id}")
    public Guest updateGuest(@PathVariable Long id, @RequestBody Guest guest) throws GuestNotFoundException {
        return guestService.updateGuest(id, guest);
    }


    @DeleteMapping("/{id}")
    public void deleteGuest(@PathVariable Long id) {
        guestService.deleteGuest(id);
    }
}
