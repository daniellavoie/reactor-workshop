package dev.daniellavoie.reactor.workshop.coinbase;

import dev.daniellavoie.reactor.workshop.coinbase.model.OrderBookEvent;
import dev.daniellavoie.reactor.workshop.coinbase.model.ProductId;
import reactor.core.publisher.Flux;

public interface OrderBookEventService {
	Flux<OrderBookEvent> getEvents(ProductId[] productsIds);
}
