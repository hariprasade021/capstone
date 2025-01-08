package com.ust.eventorganizer.Repository;

import com.ust.eventorganizer.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget,Long>
{
    Budget findByEventId(Long eventId);
}
