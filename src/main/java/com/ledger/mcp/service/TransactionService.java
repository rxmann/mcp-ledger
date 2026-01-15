package com.ledger.mcp.service;

import com.ledger.mcp.model.Transaction;
import com.ledger.mcp.repository.TransactionRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    @Tool(description = "Create a new transaction with description, amount, category, and date.")
    public Transaction createTransaction(String description, BigDecimal amount, String category, LocalDateTime transactionDate) {
        LocalDateTime now = LocalDateTime.now();
        Transaction tx = Transaction.builder()
                .description(description)
                .amount(amount)
                .category(category)
                .transactionDate(transactionDate != null ? transactionDate : now)
                .build();

        return repository.save(tx);
    }

    @Tool(description = "Get a transaction by its ID.")
    public Optional<Transaction> getTransaction(Long id) {
        return repository.findById(id);
    }

    @Tool(description = "List all transactions.")
    public List<Transaction> listTransactions() {
        return repository.findAll();
    }

    @Tool(description = "Update an existing transaction by ID with new values.")
    public Transaction updateTransaction(Long id, String description, BigDecimal amount, String category, LocalDateTime transactionDate) {
        Transaction existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found: " + id));

        Transaction updated = existing.toBuilder()
                .description(description != null ? description : existing.getDescription())
                .amount(amount != null ? amount : existing.getAmount())
                .category(category != null ? category : existing.getCategory())
                .transactionDate(transactionDate != null ? transactionDate : existing.getTransactionDate())
                .build();

        return repository.save(updated);
    }

    @Tool(description = "Delete a transaction by ID.")
    public boolean deleteTransaction(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Tool(description = "Return the total amounts grouped by category.")
    public Map<String, BigDecimal> getCategoryTotals() {
        List<Transaction> transactions = repository.findAll();
        Map<String, BigDecimal> totals = new HashMap<>();
        for (Transaction t : transactions) {
            totals.merge(t.getCategory(), t.getAmount(), BigDecimal::add);
        }
        return totals;
    }
}