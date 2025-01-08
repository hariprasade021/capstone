package com.taskService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long eventId; // Foreign key to link with an event
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status; // Pending, In Progress, Completed
    private LocalDate deadline;
}
