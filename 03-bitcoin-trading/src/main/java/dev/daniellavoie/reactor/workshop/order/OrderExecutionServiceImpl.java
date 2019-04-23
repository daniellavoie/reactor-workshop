package dev.daniellavoie.reactor.workshop.order;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.daniellavoie.reactor.workshop.model.OrderExecution;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.Sender;

@Service
public class OrderExecutionServiceImpl implements OrderExecutionService {
	private final Sender sender;
	private final String exchange;
	private final String routingKey;

	private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

	public OrderExecutionServiceImpl(Sender sender,
			@Value("${rabbitmq.order-execution.exchange:order-execution.exchange}") String exchange,
			@Value("${rabbitmq.order-execution.routing-key:#}") String routingKey) {
		this.sender = sender;
		this.exchange = exchange;
		this.routingKey = routingKey;
	}

	@Override
	public Mono<Void> sendOrderExecutionMessage(OrderExecution orderExecution) {
		// Step 1 - Generate an OutboundMessage.

		// Step 2 - Send the output message using a Sender.
		return null;
	}

	private byte[] writeValue(OrderExecution orderExecution) {
		try {
			return objectMapper.writeValueAsBytes(orderExecution);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
