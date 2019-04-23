package dev.daniellavoie.reactor.workshop.coinbase.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ProductId {
	@JsonProperty("BTC-USD")
	BTC_USD,

	@JsonProperty("ETH-EUR")
	ETH_EUR,

	@JsonProperty("ETH-USD")
	ETH_USD;
}
