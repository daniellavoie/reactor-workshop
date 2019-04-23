package dev.daniellavoie.reactor.workshop.order;

import java.time.Duration;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.ToDoubleFunction;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import dev.daniellavoie.reactor.workshop.ReactorWorkshopApplication;
import dev.daniellavoie.reactor.workshop.coinbase.OrderBookNotification;
import dev.daniellavoie.reactor.workshop.coinbase.model.ProductId;
import dev.daniellavoie.reactor.workshop.order.OrderBookServiceImpl;
import reactor.test.StepVerifier;
import reactor.test.StepVerifier.Step;

@SpringBootTest(classes = ReactorWorkshopApplication.class, properties = "coinbase.mock.enabled=true")
@RunWith(SpringRunner.class)
public class OrderBookServiceImplTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderBookServiceImplTest.class);

	@Autowired
	private OrderBookServiceImpl orderBookService;

	private OrderBookNotification previousNotification;
	private AtomicInteger count = new AtomicInteger();

	@Test
	public void assertOrderBookStream() {
		try {
			final int EXPECTED_COUNT = 141;

			Step<OrderBookNotification> step = StepVerifier.create(orderBookService.orderBookStream(ProductId.values())
					.filter(orderBookNotification -> orderBookNotification.getOrderBookEvent().getProductId()
							.equals(ProductId.ETH_USD)));

			for (int i = 0; i < EXPECTED_COUNT; i++) {
				step = step.expectNextMatches(this::assertNotification);
			}

			step.thenCancel().verify(Duration.ofSeconds(2));

			Assert.assertEquals(EXPECTED_COUNT, count.get());
		} catch (AssertionError ex) {
			LOGGER.info("Processed {} events.", count.get());

			throw ex;
		}
	}

	private boolean assertNotification(OrderBookNotification orderBookNotification) {
		try {
			if (previousNotification == null) {
				return true;
			}

			// Check for the biggest bid.
			double biggestBid = orderBookNotification.getOrderBook().getBids().stream()
					.map(entry -> entry.getPrice().doubleValue()).max(Comparator.comparingDouble(price -> price)).get();

			if (orderBookNotification.getOrderBook().getBids().iterator().next().getPrice()
					.doubleValue() != biggestBid) {
				return false;
			}

			// Check for the biggest ask.
			// Compiler cannot infer the double type when reversed function is in-lined with
			// the key extractor lambda.
			ToDoubleFunction<Double> keyExtractor = price -> price;
			double smallestAsk = orderBookNotification.getOrderBook().getAsks().stream()
					.map(entry -> entry.getPrice().doubleValue())
					.max(Comparator.comparingDouble(keyExtractor).reversed()).get();

			if (orderBookNotification.getOrderBook().getAsks().iterator().next().getPrice()
					.doubleValue() != smallestAsk) {
				return false;
			}

			return true;
		} finally {
			previousNotification = orderBookNotification;
			count.incrementAndGet();
		}
	}

}
