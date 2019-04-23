package dev.daniellavoie.reactor.workshop.coinbase;

import java.time.Duration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import dev.daniellavoie.reactor.workshop.coinbase.model.OrderBookEvent;
import dev.daniellavoie.reactor.workshop.coinbase.model.ProductId;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CoinbaseOrderBookEventServiceTest {
	@Autowired
	private CoinbaseOrderBookEventService coinbaseOrderBookEventService;

	@Test
	public void fetch10events() {

		// Retreive the order book event stream for the BTC_USD product.
		Flux<OrderBookEvent> orderBookEventStream = coinbaseOrderBookEventService
				.getEvents(new ProductId[] { ProductId.BTC_USD })

				// Send a completion signal after 10 notifications.
				.take(10);

		StepVerifier.create(orderBookEventStream)

				// Asserts 10 events were notified.
				.expectNextCount(10)

				// Asserts that the stream received a complete signal.
				.expectComplete()

				// Trigger the assertions.
				.verify(Duration.ofSeconds(5));
	}
}
