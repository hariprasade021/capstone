package com.ust.eventorganizer.Service;


import com.ust.eventorganizer.model.Budget;

import java.math.BigDecimal;
import java.util.List;

public interface BudgetService
{
    public Budget saveBudget(Budget event);
    public List<Budget> getAllBudgets();
    public void deleteBudget(Long id);
    public Budget updateBudget(Long id, Budget budget);
    public Budget getBudgetById(Long id);
    public Budget getBudgetByEventId(Long eventId);
//    public BigDecimal calculateActualExpense(Long eventId);
}
