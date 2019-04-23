package dev.daniellavoie.reactor.workshop.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Side {
	@JsonProperty("buy")
	BUY,

	@JsonProperty("sell")
	SELL;
}
