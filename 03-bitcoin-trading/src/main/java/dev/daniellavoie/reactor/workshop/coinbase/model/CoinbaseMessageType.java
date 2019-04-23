package dev.daniellavoie.reactor.workshop.coinbase.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CoinbaseMessageType {
	@JsonProperty("subscribe")
	SUBSCRIBE;
}
