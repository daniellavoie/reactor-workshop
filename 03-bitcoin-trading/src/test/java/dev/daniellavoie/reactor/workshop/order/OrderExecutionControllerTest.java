package dev.daniellavoie.reactor.workshop.order;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.daniellavoie.reactor.workshop.ReactorWorkshopApplication;
import dev.daniellavoie.reactor.workshop.model.OrderExecution;
import dev.daniellavoie.reactor.workshop.model.Side;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;
import reactor.test.StepVerifier;

@DirtiesContext
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReactorWorkshopApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class OrderExecutionControllerTest {
	private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

	@LocalServerPort
	private int port;

	@MockBean
	private Sender sender;

	private CountDownLatch latch = new CountDownLatch(1);
	private OutboundMessage message;

	@Test
	public void testWebServiceAndRabbitPayload()
			throws InterruptedException, JsonParseException, JsonMappingException, IOException {
		Mockito.when(sender.send(Mockito.any())).thenReturn(Mono.empty());

		OrderExecution request = new OrderExecution(new BigDecimal(10d), new BigDecimal(1000d), Side.BUY);

		// Asserts the endpoint replied has expected.
		StepVerifier.create(WebClient.create("http://localhost:" + port).post().uri("/order-execution")
				.syncBody(request).retrieve().bodyToMono(Void.class)).expectComplete().verify();

		// Declares a Mockito Argument Matcher that will asynchronously extract the
		// payload sent to the RabbitMQ mock and asserts that RabbitMQ was called.
		Mockito.verify(sender).send(Mockito.argThat(publisher -> {
			((Mono<OutboundMessage>) publisher).doOnSuccess(message -> this.message = message)

					.doOnSuccess(message -> latch.countDown()).subscribe();

			return true;
		}));

		// Processing is asynchronous, wait for our argument matcher to unlock the
		// latch.
		if (!latch.await(2, TimeUnit.SECONDS)) {
			throw new RuntimeException("Sender was not notified of an outbound message in time.");
		}

		// Asserts that the RabbitMQ mock did receive our payload.
		OrderExecution rabbitPayload = objectMapper.readValue(message.getBody(), OrderExecution.class);
		Assert.assertEquals(request.getAmount(), rabbitPayload.getAmount());
		Assert.assertEquals(request.getPrice(), rabbitPayload.getPrice());
		Assert.assertEquals(request.getSide(), rabbitPayload.getSide());
	}
}
