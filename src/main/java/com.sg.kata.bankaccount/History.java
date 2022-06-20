package org.account.manager.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Data
@NoArgsConstructor
@Entity
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(targetEntity = Account.class, cascade = {CascadeType.ALL})
    @JoinColumn(name = "account_id")
    private Account account;
    @Column
    private Long amount;
    @Column
    @Enumerated(EnumType.STRING)
    private Operation operation;
    @Column
    private LocalDateTime dateTime;
    @Column
    private Long previousBalance;
    @Column
    private Long newBalance;

    public History(Account account, Long amount, Long previousBalance, Long newBalance, Operation operation) {
        this.account = account;
        this.amount = amount;
        this.dateTime = now();
        this.operation = operation;
        this.previousBalance = previousBalance;
        this.newBalance = newBalance;
    }
}
