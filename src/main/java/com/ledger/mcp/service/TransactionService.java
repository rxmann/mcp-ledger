package com.ledger.mcp.service;

import com.ledger.mcp.model.Transaction;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TransactionService {

    // Simple in-memory store for demo (can replace with JPA repository)
    private final Map<Long, Transaction> store = new ConcurrentHashMap<>();
    private long idCounter = 1;

    /**
     * Create a new transaction.
     * @param description Description of the transaction
     * @param amount Amount of the transaction
     * @param category Category name
     * @param transactionDate Date of transaction
     * @return The created transaction
     */
    public Transaction createTransaction(String description,
                                         BigDecimal amount,
                                         String category,
                                         LocalDateTime transactionDate) {
        Transaction tx = new Transaction();
        tx.setId(idCounter++);
        tx.setDescription(description);
        tx.setAmount(amount);
        tx.setCategory(category);
        tx.setTransactionDate(transactionDate != null ? transactionDate : LocalDateTime.now());
        tx.setCreatedAt(LocalDateTime.now());
        tx.setUpdatedAt(LocalDateTime.now());

        store.put(tx.getId(), tx);
        return tx;
    }

    /**
     * Get a transaction by ID.
     * @param id Transaction ID
     * @return Optional transaction
     */
    public Optional<Transaction> getTransaction(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    /**
     * List all transactions.
     * @return List of all transactions
     */
    public List<Transaction> listTransactions() {
        return new ArrayList<>(store.values());
    }

    /**
     * Update an existing transaction.
     * @param id Transaction ID
     * @param description New description
     * @param amount New amount
     * @param category New category
     * @param transactionDate New transaction date
     * @return Updated transaction
     */
    public Transaction updateTransaction(Long id,
                                         String description,
                                         BigDecimal amount,
                                         String category,
                                         LocalDateTime transactionDate) {
        Transaction existing = store.get(id);
        if (existing == null) {
            throw new IllegalArgumentException("Transaction not found: " + id);
        }

        if (description != null) existing.setDescription(description);
        if (amount != null) existing.setAmount(amount);
        if (category != null) existing.setCategory(category);
        if (transactionDate != null) existing.setTransactionDate(transactionDate);

        existing.setUpdatedAt(LocalDateTime.now());
        store.put(id, existing);
        return existing;
    }

    /**
     * Delete a transaction.
     * @param id Transaction ID
     * @return true if deleted, false if not found
     */
    public boolean deleteTransaction(Long id) {
        return store.remove(id) != null;
    }
}
