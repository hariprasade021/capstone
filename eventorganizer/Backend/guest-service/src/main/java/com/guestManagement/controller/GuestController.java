package com.guestManagement.controller;


import com.guestManagement.model.Guest;
import com.guestManagement.service.GuestEmailService;
import com.guestManagement.service.GuestService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/guest")
public class GuestController
{
    @Autowired
    private GuestService guestService;

    @Autowired
    private GuestEmailService guestEmailService;

    @PostMapping
    public Guest createGuest(@RequestBody Guest guest) {
        return guestService.create(guest);
    }

    @GetMapping("/{id}")
    public Optional<Guest> getGuestById(@PathVariable Long id) {
        return guestService.getGuestById(id);
    }

    @GetMapping
    public List<Guest> getAllGuests() {
        return guestService.getAll();
    }

    @PutMapping("/{id}")
    public Optional<Guest> updateGuest(@PathVariable Long id, @RequestBody Guest guest) {
        return guestService.update(id, guest);
    }

    @DeleteMapping("/{id}")
    public void deleteGuest(@PathVariable Long id) {
        guestService.delete(id);
    }
//
//    @GetMapping("/event/{eventId}")
//    public List<Guest> getGuestsByEventId(@PathVariable String eventId) {
//        return guestService.findByEventId(eventId);
//    }

//    @GetMapping("/diet/{dietaryPreference}")
//    public List<Guest> getGuestsByDietaryPreference(@PathVariable String dietaryPreference) {
//        return guestService.findGuestsByDietaryPreference(dietaryPreference);
//    }


    @PostMapping("/{id}/send-invitation")
    public ResponseEntity<String> sendInvitation(
            @PathVariable Long id,
            @RequestParam String eventName,
            @RequestParam String eventDate,
            @RequestParam String eventLocation
    ) {
        try {
            // Retrieve guest by ID
            Guest guest = guestService.getGuestById(id)
                    .orElseThrow(() -> new RuntimeException("Guest not found"));

            // Send invitation
            guestEmailService.sendEventInvitation(guest, eventName, eventDate, eventLocation);

            return ResponseEntity.ok("Invitation sent successfully to " + guest.getEmail());
        } catch (MessagingException e) {
            return ResponseEntity.badRequest().body("Failed to send invitation: " + e.getMessage());
        }
    }
}
