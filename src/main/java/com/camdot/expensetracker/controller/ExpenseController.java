package com.camdot.expensetracker.controller;

import com.camdot.expensetracker.model.Expense;
import com.camdot.expensetracker.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("sum")
    @Operation(summary = "Get sum of all expenses")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Sum of all expenses")})
    public ResponseEntity<Long> getSumOfAllExpenses() {
        long sum = expenseService.getSumOfAllExpenses();
        return ResponseEntity.status(HttpStatus.OK).body(sum);
    }

    @GetMapping
    @Operation(summary = "Get all expenses")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of expenses")})
    public ResponseEntity<List<Expense>> getAllExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();
        return ResponseEntity.status(HttpStatus.OK).body(expenses);
    }

    @PostMapping
    @Operation(summary = "Create a new expense")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Expense created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
        Expense createdExpense = expenseService.createExpense(expense);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExpense);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modify an existing expense")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Expense modified successfully"),
            @ApiResponse(responseCode = "404", description = "Expense not found")})
    public ResponseEntity<Expense> modifyExpense(@PathVariable Long id, @RequestBody Expense expense) {
        Expense modifiedExpense = expenseService.modifyExpense(expense);
        return ResponseEntity.ok(modifiedExpense);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an expense")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Expense deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Expense not found")})
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}