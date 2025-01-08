package com.ust.eventorganizer.Controller;

import com.ust.eventorganizer.Service.EventReportServiceImpl;
import com.ust.eventorganizer.Service.EventService;
import com.ust.eventorganizer.Service.EventServiceImpl;
import com.ust.eventorganizer.Service.VendorService;
import com.ust.eventorganizer.exception.EventNotFoundException;
import com.ust.eventorganizer.exception.GuestNotFoundException;
import com.ust.eventorganizer.exception.VendorNotFoundException;
import com.ust.eventorganizer.model.Event;
import com.ust.eventorganizer.model.EventReport;
import com.ust.eventorganizer.model.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
public class EventController
{
    @Autowired
    private EventService eventService;

    @Autowired
    private EventServiceImpl eventServiceImpl;

    @Autowired
    private EventReportServiceImpl eventReport;

    @PostMapping
    public Event createEvent(@RequestBody Event event)
    {
        return eventService.saveEvent(event);
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Long id) throws EventNotFoundException {
        return eventService.getEventById(id);
    }


    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable Long id, @RequestBody Event event) throws EventNotFoundException {
        return eventService.updateEvent(id, event);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id)
    {
        eventService.deleteEvent(id);
    }

    @GetMapping("/search")
    public List<Event> searchEventsByName(@RequestParam String name) throws EventNotFoundException {
        return eventService.searchEventsByName(name);
    }

    @GetMapping("/search-by-date")
    public List<Event> getEventsByDateRange( @RequestParam("start") String startDateStr,
            @RequestParam("end") String endDateStr) throws EventNotFoundException {

        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);
        return eventService.searchEventsByDateRange(startDate, endDate);
    }

    @PutMapping("/{eventId}/update-budget-actual-expense")
    public Event updateActualExpenseInBudget(@PathVariable Long eventId) throws EventNotFoundException {
        return eventServiceImpl.updateActualExpenseInBudget(eventId);
    }

    @GetMapping("/{id}/summary")
    public EventReport getEventSummary(@PathVariable Long id) throws EventNotFoundException, GuestNotFoundException, VendorNotFoundException {
        return eventReport.getEventSummary(id);
    }


}
