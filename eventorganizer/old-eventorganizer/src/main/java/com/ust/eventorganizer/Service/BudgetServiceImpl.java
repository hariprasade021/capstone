package com.ust.eventorganizer.Service;

import com.ust.eventorganizer.Repository.BudgetRepository;
import com.ust.eventorganizer.Repository.EventRepository;
import com.ust.eventorganizer.exception.GuestNotFoundException;
import com.ust.eventorganizer.model.Budget;
import com.ust.eventorganizer.model.Event;
import com.ust.eventorganizer.model.Guest;
import com.ust.eventorganizer.model.Vendor;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class BudgetServiceImpl implements BudgetService{
    @Autowired
    private BudgetRepository budgetRepo;

    @Autowired
    private EventRepository eventRepo;

    @Override
    public Budget saveBudget(Budget budget) {
        return budgetRepo.save(budget);
    }

    @Override
    public List<Budget> getAllBudgets() {
        return budgetRepo.findAll();
    }

    @Override
    public void deleteBudget(Long id) {
        if (!budgetRepo.existsById(id)) {
            throw new RuntimeException("Budget ID does not exist");
        }
        budgetRepo.deleteById(id);
    }


    public Budget updateBudget(Long id, Budget budget) {


        if(!budgetRepo.existsById(id))
        {
            throw new RuntimeException("Budget with ID: "+id+" not found");
        }

        Budget budgetToUpdate = getBudgetById(id);
        if(budget.getEstimatedExpense()!=null)
        {
            budgetToUpdate.setEstimatedExpense(budget.getEstimatedExpense());
        }
        if(budget.getActualExpense()!=null)
        {
            budgetToUpdate.setActualExpense(budget.getActualExpense());
        }
        return budgetRepo.save(budgetToUpdate);
    }

    @Override
    public Budget getBudgetById(Long id) {
        
        return budgetRepo.findById(id).orElseThrow(null);
    }

    @Override
    public Budget getBudgetByEventId(Long eventId) {
        return budgetRepo.findByEventId(eventId);
    }


}
