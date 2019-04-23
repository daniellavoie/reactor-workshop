package dev.daniellavoie.reactor.workshop.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Trade {
	@Id
	private String id;
	private OrderExecution orderExecution;
	private LocalDateTime tradeDate;

	public Trade() {

	}

	public Trade(String id, OrderExecution orderExecution, LocalDateTime tradeDate) {
		this.id = id;
		this.orderExecution = orderExecution;
		this.tradeDate = tradeDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public OrderExecution getOrderExecution() {
		return orderExecution;
	}

	public void setOrderExecution(OrderExecution orderExecution) {
		this.orderExecution = orderExecution;
	}

	public LocalDateTime getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(LocalDateTime tradeDate) {
		this.tradeDate = tradeDate;
	}
}
