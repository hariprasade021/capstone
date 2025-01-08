package com.eventManagement.feignclient;

import com.eventManagement.dto.TaskDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "TASK-SERVICE")
public interface TaskFeign
{
    @GetMapping("api/task/event/{eventId}")
    List<TaskDTO> getTasksByEventId(@PathVariable("eventId") Long eventId);
}
