package com.camdot.expensetracker.controller;

import com.camdot.expensetracker.AbstractApplicationTest;
import com.camdot.expensetracker.model.Expense;
import com.camdot.expensetracker.repository.ExpenseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
public class ExpenseControllerTest extends AbstractApplicationTest {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Expense createExpense(String description, long amount) {
        return new Expense(description, amount, OffsetDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC));
    }

    @Test
    public void getAllExpenses_shouldReturn200WithListOfExpenses() throws Exception {
        List<Expense> expenses = List.of(
            createExpense("Expense 1", 100l),
            createExpense("Expense 2", 100l)
        );
        expenseRepository.saveAll(expenses);

        mockMvc.perform(get("/api/expenses"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].description").value("Expense 1"))
            .andExpect(jsonPath("$[1].description").value("Expense 2"));
    }

    @Test
    public void createExpense_shouldReturn201WhenSuccessful() throws Exception {
        Expense expense = createExpense("Test Expense", 100l);
        String expenseJson = objectMapper.writeValueAsString(expense);

        mockMvc.perform(post("/api/expenses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(expenseJson))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.description").value("Test Expense"));

        List<Expense> expenses = expenseRepository.findByDescriptionContaining("Test Expense");
        Assertions.assertEquals(1, expenses.size());
    }

    @Test
    public void modifyExpense_shouldReturn200WhenSuccessful() throws Exception {
        Expense expense = expenseRepository.save(createExpense("Initial Expense", 100L));

        Expense modifiedExpense = createExpense("Updated Expense", 200L);
        modifiedExpense.setId(expense.getId());
        String modifiedExpenseJson = objectMapper.writeValueAsString(modifiedExpense);

        mockMvc.perform(put("/api/expenses/" + expense.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(modifiedExpenseJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description").value("Updated Expense"));

        Expense updatedExpense = expenseRepository.findById(expense.getId()).get();
        Assertions.assertEquals("Updated Expense", updatedExpense.getDescription());
    }

    @Test
    public void modifyExpense_shouldReturn404WhenExpenseNotFound() throws Exception {
        Expense modifiedExpense = createExpense("Updated Expense", 200L);
        String modifiedExpenseJson = objectMapper.writeValueAsString(modifiedExpense);

        mockMvc.perform(put("/api/expenses/999")
            .contentType(MediaType.APPLICATION_JSON)
            .content(modifiedExpenseJson))
            .andExpect(status().isNotFound());
    }

    @Test
    public void deleteExpense_shouldReturn204WhenSuccessful() throws Exception {
        Expense expense = expenseRepository.save(createExpense("Test Expense", 100L));

        mockMvc.perform(delete("/api/expenses/" + expense.getId()))
            .andExpect(status().isNoContent());

        boolean exists = expenseRepository.existsById(expense.getId());
        Assertions.assertEquals(false, exists);
    }

    @Test
    public void deleteExpense_shouldReturn404WhenExpenseNotFound() throws Exception {
        mockMvc.perform(delete("/api/expenses/999"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void getSumOfAllExpenses_shouldReturn200WithSumOfAllExpenses() throws Exception {
        List<Expense> expenses = List.of(
            createExpense("Test Expense", 100L),
            createExpense("Test Expense", 200L)
        );

        expenseRepository.saveAll(expenses);

        mockMvc.perform(get("/api/expenses/sum"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").value(300));
    }
}