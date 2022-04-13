package org.account.manager.models;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.account.manager.models.Operation.*;

@Data
@Entity
@Slf4j
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id")
	private Long id;
	@Column
	private String number;
	@Column
	private Long balance;
	@OneToMany(cascade = {CascadeType.ALL}, mappedBy = "account")
	@Fetch(FetchMode.SUBSELECT)
	private List<History> history = new ArrayList<>();

	public Account() {
		number = generateNumber();
		balance = 0L;
		history.add(new History(this, null, null, 0f, CREATE));
	}

	public void withdraw(Long amount) {
		Long previousBalance = balance;
		balance -= amount;
		history.add(new History(this, amount, previousBalance, balance, WITHDRAW));
	}

	public void deposit(Long amount) {
		Long previousBalance = balance;
		balance += amount;
		history.add(new History(this, amount, previousBalance, balance, DEPOSIT));
	}

	private String generateNumber() {
		return "SG_" + Timestamp.valueOf(now()).getTime();
	}
}
