package com.feedbackService.feignClient;

import com.feedbackService.dto.EventDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "event-service")
public interface EventFeign {
    @GetMapping("api/event/{eventId}")
    EventDTO getEventById(@PathVariable Long eventId);
}