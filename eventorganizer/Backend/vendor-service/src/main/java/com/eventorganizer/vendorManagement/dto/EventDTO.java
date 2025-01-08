package com.eventorganizer.vendorManagement.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class EventDTO {
    private Long id;
    private Long userId;
    private String name;
    private String title;
    private String location;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Long> vendorIds = new ArrayList<>();
    private List<Long> guestIds = new ArrayList<>();
}
