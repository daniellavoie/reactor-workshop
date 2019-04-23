package dev.daniellavoie.reactor.workshop.coinbase.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CoinbaseChannelName {
	@JsonProperty("level2")
	LEVEL_2,
	
	@JsonProperty("ticker")
	TICKER;
}
