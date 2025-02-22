package com.camdot.expensetracker.repository;

import com.camdot.expensetracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByDescriptionContaining(String description);
    List<Expense> findByAmount(Long amount);
    List<Expense> findByAmountBetween(Long minAmount, Long maxAmount);
    List<Expense> findByDateTime(OffsetDateTime dateTime);
    List<Expense> findByDateTimeBetween(OffsetDateTime startDate, OffsetDateTime endDate);
}


