package com.ClientService.ClientService.feignClient;

import com.feedbackService.dto.EventFeedbackDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "FEEDBACK-SERVICE")
public interface FeedbackFeign {
    @GetMapping("api/feedback/event/{eventId}")
    List<EventFeedbackDTO> getFeedbackByEventId(@PathVariable Long eventId);
}
