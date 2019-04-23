package dev.daniellavoie.reactor.workshop.coinbase;

import java.net.URI;

import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;

import dev.daniellavoie.reactor.workshop.coinbase.model.OrderBookEvent;
import dev.daniellavoie.reactor.workshop.coinbase.model.ProductId;
import reactor.core.publisher.Flux;

public class CoinbaseOrderBookEventService implements OrderBookEventService {
	private final CoinbaseConfiguration coinbaseConfiguration;

	private final ReactorNettyWebSocketClient webSocketClient = new ReactorNettyWebSocketClient();

	public CoinbaseOrderBookEventService(CoinbaseConfiguration coinbaseConfiguration) {
		this.coinbaseConfiguration = coinbaseConfiguration;

		// Snapshots payload from coinbase are huuuuuuuuuuuuuge.
		webSocketClient.setMaxFramePayloadLength(65536 * 10);

	}

	@Override
	public Flux<OrderBookEvent> getEvents(ProductId[] productsIds) {
		return Flux.create(sink -> webSocketClient

				.execute(URI.create(coinbaseConfiguration.getWebsocketUrl()),
						new CoinbaseWebSocketHandler(productsIds, sink))

				.doOnError(sink::error)

				.subscribe());
	}
}
