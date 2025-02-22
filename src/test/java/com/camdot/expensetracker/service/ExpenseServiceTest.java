package com.camdot.expensetracker.service;

import com.camdot.expensetracker.model.Expense;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@SpringBootTest
public class ExpenseServiceTest {
    // This should be expanded into more tests. Though the controller acts as an e2e test so it's not necessarily needed. For such a small app this is okay, but not ideal for future

    @Autowired
    private ExpenseService expenseService;

    @Test
    public void test() {
        Expense expense = createExpense("Test Expense", 100L);
        expenseService.createExpense(expense);
    }

    public Expense createExpense(String description, long amount) {
        return new Expense(
            description,
            amount,
            OffsetDateTime.of(
                2025, 1, 1,
                0, 0, 0,
                0, ZoneOffset.UTC)
        );
    }
}