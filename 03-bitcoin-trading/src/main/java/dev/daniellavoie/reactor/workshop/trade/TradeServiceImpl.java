package dev.daniellavoie.reactor.workshop.trade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

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
		// Step 1 - subscribe with auto-ack to the queue.

		// Step 2 - Access the message body.

		// Step 3 - unmarshal the message body.

		// Step 4 - Transform the execution order into an actual trade.

		// Step 5 - Persist the trade to MongoDB

		// Step 6 - Log any error that might occurs while processing the queue.

	}
}
