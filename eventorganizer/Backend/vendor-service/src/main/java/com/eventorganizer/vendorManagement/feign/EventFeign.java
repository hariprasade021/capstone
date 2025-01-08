package com.eventorganizer.vendorManagement.feign;

import com.eventorganizer.vendorManagement.dto.EventDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "EVENT-SERVICE") // Replace with Event Service URL
public interface EventFeign
{

    @GetMapping("/api/events/{id}")
    EventDTO getEventById(@PathVariable("id") Long eventId);


    @PutMapping("/api/events/{id}")
    EventDTO updateEvent(@PathVariable("id") Long eventId, @RequestBody EventDTO updatedEvent);

}