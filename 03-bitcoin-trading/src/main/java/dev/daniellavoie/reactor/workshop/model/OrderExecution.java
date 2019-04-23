package dev.daniellavoie.reactor.workshop.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderExecution {
	private final BigDecimal price;
	private final BigDecimal amount;
	private final Side side;

	@JsonCreator
	public OrderExecution(@JsonProperty("price") BigDecimal price, @JsonProperty("amount") BigDecimal amount,
			@JsonProperty("side") Side side) {
		this.price = price;
		this.amount = amount;
		this.side = side;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Side getSide() {
		return side;
	}
}
