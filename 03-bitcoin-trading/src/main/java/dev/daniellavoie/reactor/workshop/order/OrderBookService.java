package dev.daniellavoie.reactor.workshop.order;

import dev.daniellavoie.reactor.workshop.coinbase.OrderBookNotification;
import dev.daniellavoie.reactor.workshop.coinbase.model.ProductId;
import reactor.core.publisher.Flux;

public interface OrderBookService {
	Flux<OrderBookNotification> orderBookStream(ProductId[] productIds);
}
