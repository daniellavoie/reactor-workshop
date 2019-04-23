package dev.daniellavoie.reactor.workshop.order;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

import dev.daniellavoie.reactor.workshop.ReactorWorkshopApplication;
import dev.daniellavoie.reactor.workshop.coinbase.OrderBookNotification;

@SpringBootTest(classes = ReactorWorkshopApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class OrderBookControllerTest {

	@LocalServerPort
	private int port;

	@Test
	public void assertReactiveness() throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(20);

		WebClient.create("http://localhost:" + port).get().uri("/order-book").retrieve()
				.bodyToFlux(OrderBookNotification.class).skip(1).log().take(20).log()
				.doOnNext(notification -> latch.countDown()).doOnSubscribe(subscriber -> {
					WebClient.create("http://localhost:" + port).get().uri("/order-book").retrieve()
							.bodyToFlux(OrderBookNotification.class).skip(1)
							.map(notification -> "Concurrent web client notification.").take(20).log().subscribe();
				}).subscribe();

		latch.await(5, TimeUnit.SECONDS);
	}
}
