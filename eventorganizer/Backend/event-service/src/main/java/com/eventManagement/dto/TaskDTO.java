package com.eventManagement.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskDTO
{
    private Long id;
    private Long eventId;
    private String name;
    private String description;
    private String status; // Pending, In Progress, Completed
    private LocalDate deadline;
}