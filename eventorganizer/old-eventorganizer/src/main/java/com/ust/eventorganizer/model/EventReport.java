package com.ust.eventorganizer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventReport
{
    private Event event;
    private BigDecimal totalExpense;
    private List<Guest> attendingGuests;
    private List<Vendor> vendors;
    private Budget budget;
    private List<Feedback> feedbacks;
}

