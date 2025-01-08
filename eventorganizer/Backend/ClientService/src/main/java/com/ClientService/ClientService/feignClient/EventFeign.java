package com.ClientService.ClientService.feignClient;

import com.eventManagement.dto.EventDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(name = "EVENT-SERVICE")
//public interface EventFeign {
////    @GetMapping("api/events/{eventId}")
////    EventDTO getEventById(@PathVariable Long eventId);
//}

@FeignClient(name = "EVENT-SERVICE")
public interface EventFeign {

    @PostMapping("api/events/assignEventToClient")
    void assignEventToClient(@RequestParam("eventId") Long eventId,@RequestParam("clientId") Long clientId);
}

//path = "/api/event"