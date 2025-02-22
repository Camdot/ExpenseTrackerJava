package com.camdot.expensetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ExpenseTrackerJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpenseTrackerJavaApplication.class, args);
    }

}
