package com.ust.eventorganizer.Controller;


import com.ust.eventorganizer.Service.EventService;
import com.ust.eventorganizer.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/assign")
public class AssignController {

    @Autowired
    private EventService eventService;

    @PostMapping("/{eventId}/vendors/{vendorId}")
    public Event assignVendorToEvent(@PathVariable Long eventId, @PathVariable Long vendorId) {
        return eventService.assignVendorToEvent(eventId, vendorId);
    }

    @PostMapping("/{eventId}/guests/{guestId}")
    public Event assignGuestToEvent(@PathVariable Long eventId, @PathVariable Long guestId) {
        return eventService.assignGuestToEvent(eventId, guestId);
    }

    @PostMapping("/{eventId}/feedbacks/{feedbackId}")
    public Event assignFeedbackToEvent(@PathVariable Long eventId, @PathVariable Long feedbackId) {
        return eventService.assignFeedbackToEvent(eventId, feedbackId);
    }


    @PostMapping("/{eventId}/budgets/{budgetId}")
    public Event assignBudgetToEvent(@PathVariable Long eventId, @PathVariable Long budgetId) {
        return eventService.assignBudgetToEvent(eventId, budgetId);
    }




}
