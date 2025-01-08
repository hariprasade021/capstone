package com.ust.eventorganizer.Service;

import com.ust.eventorganizer.exception.EventNotFoundException;
import com.ust.eventorganizer.model.EventReport;

public interface EventReportService
{
    EventReport getEventSummary(Long eventId) throws EventNotFoundException;
}
