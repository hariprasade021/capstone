package com.ust.eventorganizer.Service;

import com.ust.eventorganizer.Repository.BudgetRepository;
import com.ust.eventorganizer.model.Budget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetReminderService  {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private EmailService emailService;

    @Scheduled(fixedRate = 60000) // Check every minute (adjust as needed)
    public void checkBudgets() {
        List<Budget> budgets = budgetRepository.findAll();
        for (Budget budget : budgets) {
            // Compare actual and estimated expenses using compareTo
            if (budget.getActualExpense().compareTo(budget.getEstimatedExpense()) > 0) {
                sendReminder(budget);
            }
        }
    }

    private void sendReminder(Budget budget)
    {
        String subject = "Budget Exceeded Reminder";
        String text = "The actual expense for the event '" + budget.getEvent().getTitle() +
                "' has exceeded the estimated expense. Actual: " + budget.getActualExpense() +
                ", Estimated: " + budget.getEstimatedExpense() + ".";

        try
        {
            emailService.sendReminderEmail("sujithauamapthy@gmail.com", subject, text); // Change to actual recipient email
        }
        catch (MailException e)
        {
            // Handle email sending error (logging, etc.)
            e.printStackTrace();
        }
    }
}
