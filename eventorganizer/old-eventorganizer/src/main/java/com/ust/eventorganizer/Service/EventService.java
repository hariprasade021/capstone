package com.ust.eventorganizer.Service;

import com.ust.eventorganizer.Repository.EventRepository;
import com.ust.eventorganizer.exception.EventNotFoundException;
import com.ust.eventorganizer.model.Event;
import com.ust.eventorganizer.model.Vendor;

import java.time.LocalDate;
import java.util.List;

public interface EventService
{
    public Event saveEvent(Event event);
    public List<Event>getAllEvents();
    public Event getEventById(Long id) throws EventNotFoundException;
    public void deleteEvent(Long id);
    public Event updateEvent(Long id, Event event) throws EventNotFoundException;
   public Event assignVendorToEvent(Long eventId, Long vendorId);
    public Event assignBudgetToEvent(Long eventId, Long budgetId);
    public Event assignFeedbackToEvent(Long eventId, Long feedbackId);
    public Event assignGuestToEvent(Long eventId, Long guestId);
    public Event removeBudgetFromEvent(Long eventId);
    public Event removeFeedbackFromEvent(Long eventId, Long feedbackId);
    public Event removeGuestFromEvent(Long eventId, Long guestId);
    public Event removeVendorFromEvent(Long eventId, Long vendorId);
    public List<Event> searchEventsByName(String name) throws EventNotFoundException;
    public List<Event> searchEventsByDateRange(LocalDate startDate, LocalDate endDate) throws EventNotFoundException;
}
