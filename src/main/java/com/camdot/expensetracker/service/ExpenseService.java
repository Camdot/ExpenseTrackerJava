package com.camdot.expensetracker.service;

import com.camdot.expensetracker.model.Expense;
import com.camdot.expensetracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public Optional<Expense> getExpenseById(Long id) {
        if (id == null) return Optional.empty();
        return expenseRepository.findById(id);
    }

    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public Expense modifyExpense(Expense expense) {
        if (!getExpenseById(expense.getId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found");
        }
        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long id) {
        if (!getExpenseById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found");
        }
        expenseRepository.deleteById(id);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public long getSumOfAllExpenses() {
        long total = 0;
        for (Expense expense : getAllExpenses()) {
            total += expense.getAmount();
        }
        return total;
    }
}
