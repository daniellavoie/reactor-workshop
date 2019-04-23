package dev.daniellavoie.reactor.workshop.order;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.daniellavoie.reactor.workshop.model.OrderExecution;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/order-execution")
public class OrderExecutionController {
	private final OrderExecutionService orderExecutionService;

	public OrderExecutionController(OrderExecutionService orderExecutionService) {
		this.orderExecutionService = orderExecutionService;
	}

	@PostMapping
	public Mono<Void> executeOrder(@RequestBody Mono<OrderExecution> orderExecutionPublisher) {
		// Step 1 - Send an order execution message when the request body is notified.
		return null;
	}
}
