
package dev.daniellavoie.reactor.workshop.order;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.daniellavoie.reactor.workshop.coinbase.OrderBookNotification;
import dev.daniellavoie.reactor.workshop.coinbase.model.ProductId;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/order-book")
public class OrderBookController {
	private OrderBookService orderBookService;

	public OrderBookController(OrderBookService orderBookService) {
		this.orderBookService = orderBookService;
	}

	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<OrderBookNotification> orderBookNotification() {
		return orderBookService.orderBookStream(ProductId.values());
	}
}
