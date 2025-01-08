package com.ust.eventorganizer.Controller;

import com.ust.eventorganizer.Service.EventService;
import com.ust.eventorganizer.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/removeassign")
public class removeAssign {

    @Autowired
    private EventService eventService;

    @DeleteMapping("/{eventId}/budgets")
    public Event removeBudgetFromEvent(@PathVariable Long eventId) {
        return eventService.removeBudgetFromEvent(eventId);
    }
    @DeleteMapping("/{eventId}/feedbacks/{feedbackId}")
    public Event removeFeedbackFromEvent(@PathVariable Long eventId, @PathVariable Long feedbackId) {
        return eventService.removeFeedbackFromEvent(eventId, feedbackId);
    }

    @DeleteMapping("/{eventId}/guests/{guestId}")
    public Event removeGuestFromEvent(@PathVariable Long eventId, @PathVariable Long guestId) {
        return eventService.removeGuestFromEvent(eventId, guestId);
    }

    @DeleteMapping("/{eventId}/vendors/{vendorId}")
    public Event removeVendorFromEvent(@PathVariable Long eventId, @PathVariable Long vendorId) {
        return eventService.removeVendorFromEvent(eventId, vendorId);
    }

}
