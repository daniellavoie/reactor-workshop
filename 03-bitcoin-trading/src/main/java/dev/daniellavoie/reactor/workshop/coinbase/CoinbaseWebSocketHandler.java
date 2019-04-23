package dev.daniellavoie.reactor.workshop.coinbase;

import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.daniellavoie.reactor.workshop.coinbase.model.OrderBookEvent;
import dev.daniellavoie.reactor.workshop.coinbase.model.ProductId;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

public class CoinbaseWebSocketHandler implements WebSocketHandler {

	private final ProductId[] productsIds;
	private final FluxSink<OrderBookEvent> sink;

	private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

	public CoinbaseWebSocketHandler(ProductId[] productsIds, FluxSink<OrderBookEvent> sink) {
		this.productsIds = productsIds;
		this.sink = sink;
	}

	@Override
	public Mono<Void> handle(WebSocketSession session) {
		// Step 1 - Send a subscribe message

		// Step 2 - Subscribe to the

		// Step 3 - Convert notification to text

		// Step 4 - unmarshal the payload

		// Step 5 - Forward notifications

		// Step 6 - Forward error

		// Step 7 - Forward completion signal
		return null;
	}
}
