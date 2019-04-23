package dev.daniellavoie.reactor.workshop.coinbase;

import java.io.IOException;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.daniellavoie.reactor.workshop.coinbase.mock.MockedOrderBookEventService;

@Configuration
public class CoinbaseConfig {
	@Bean
	@ConditionalOnProperty(name = "coinbase.mock.enabled", matchIfMissing = true, havingValue = "false")
	public OrderBookEventService orderBookEventService(CoinbaseConfiguration coinbaseConfiguration) {
		return new CoinbaseOrderBookEventService(coinbaseConfiguration);
	}

	@Bean
	@ConditionalOnProperty(name = "coinbase.mock.enabled")
	public MockedOrderBookEventService mockedOrderBookEventService() throws IOException {
		return new MockedOrderBookEventService();
	}
}
