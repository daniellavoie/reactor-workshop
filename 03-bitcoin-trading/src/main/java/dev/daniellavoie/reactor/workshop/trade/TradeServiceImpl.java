package dev.daniellavoie.reactor.workshop.trade;

import java.io.IOException;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.daniellavoie.reactor.workshop.model.OrderExecution;
import dev.daniellavoie.reactor.workshop.model.Trade;
import reactor.rabbitmq.Receiver;

@Service
public class TradeServiceImpl implements TradeService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TradeServiceImpl.class);
	
	private final TradeRepository tradeRepository;
	private final Receiver receiver;
	private final String queue;

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public TradeServiceImpl(TradeRepository tradeRepository, Receiver receiver,
			@Value("${rabbitmq.order-execution.exchange:order-execution.queue}") String queue) {
		this.tradeRepository = tradeRepository;
		this.receiver = receiver;
		this.queue = queue;
	}

	@Override
	public void initialize() {
		receiver.consumeAutoAck(queue)

				.map(delivery -> delivery.getBody())

				.map(this::readValue)

				.map(orderExecution -> new Trade(null, orderExecution, LocalDateTime.now()))

				.flatMap(tradeRepository::save)

				.doOnError(throwable -> LOGGER.error("Error while processing order executions.", throwable))

				.subscribe();
	}

	public OrderExecution readValue(byte[] bytes) {
		try {
			return objectMapper.readValue(bytes, OrderExecution.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
