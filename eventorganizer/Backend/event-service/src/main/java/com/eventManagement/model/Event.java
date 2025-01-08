package com.eventManagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@Entity
public class Event
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String name;
    private String title;
    private String location;
    private String description;
    private Double estimatedExpense;
    private Double actualExpense;

    //    @Temporal(TemporalType.TIMESTAMP)
//    @DateTimeFormat(style = "dd-MM-yyyy hh:mm")
//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy hh:mm")
    private LocalDate startDate;
    private LocalDate endDate;
    private EventType type;
    private Status status;

//    private Long clientId;

    @ElementCollection(fetch = FetchType.EAGER)
            @JsonProperty("vendorIds")
    Set<Long> vendors=new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    List<Long>guests=new ArrayList<>();

//    @ElementCollection
//    private List<Long> clientIds = new ArrayList<>();

//
//    @Transient
//    private double totalValue;


}
