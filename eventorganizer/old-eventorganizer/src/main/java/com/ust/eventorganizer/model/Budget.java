package com.ust.eventorganizer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Budget
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal estimatedExpense; //This is our budget

    @Column(precision = 12, scale = 2)
    private BigDecimal actualExpense=BigDecimal.ZERO; //This is the price coming from vendor

    @OneToOne
    @JoinColumn(name = "event_id")
    @JsonIgnore //mod4
    private Event event;



}
