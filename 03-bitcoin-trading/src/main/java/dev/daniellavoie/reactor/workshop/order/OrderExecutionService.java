package dev.daniellavoie.reactor.workshop.order;

import dev.daniellavoie.reactor.workshop.model.OrderExecution;
import reactor.core.publisher.Mono;

public interface OrderExecutionService {
	Mono<Void> sendOrderExecutionMessage(OrderExecution orderExecution);
}
