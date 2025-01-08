package com.ust.eventorganizer.Service;

import com.ust.eventorganizer.exception.EventNotFoundException;
import com.ust.eventorganizer.exception.GuestNotFoundException;
import com.ust.eventorganizer.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class EventReportServiceImpl implements EventReportService
{

    @Autowired
    private EventService eventService;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private GuestService guestService;

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private FeedbackService feedbackService;


    @Override
    public EventReport getEventSummary(Long eventId) throws EventNotFoundException {
        Event event = eventService.getEventById(eventId);

        // Fetch vendors associated with the event
        List<Vendor> vendors = vendorService.getAllVendorsForEvent(eventId);
        BigDecimal totalExpense = vendors.stream()
                .map(Vendor::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Fetch guests who are attending
        List<Guest> attendingGuests = guestService.getAllGuestsForEvent(eventId)
                .stream()
                .filter(guest -> guest.getRsvpStatus() == Rsvp.ATTENDING)
                .collect(Collectors.toList());

        // Fetch the budget details
        Budget budget = budgetService.getBudgetByEventId(eventId);

        // Fetch feedback associated with the event
        List<Feedback> feedbacks = feedbackService.getFeedbackByEventId(eventId);

        // Create the event report
        EventReport report = new EventReport();
        report.setEvent(event);
        report.setTotalExpense(totalExpense);
        report.setAttendingGuests(attendingGuests);
        report.setVendors(vendors);
        report.setBudget(budget);
        report.setFeedbacks(feedbacks);

        return report;
    }


}
