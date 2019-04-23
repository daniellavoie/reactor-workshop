package dev.daniellavoie.reactor.workshop.trade;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Delivery;

import dev.daniellavoie.reactor.workshop.ReactorWorkshopApplication;
import dev.daniellavoie.reactor.workshop.model.OrderExecution;
import dev.daniellavoie.reactor.workshop.model.Side;
import dev.daniellavoie.reactor.workshop.model.Trade;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.Receiver;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReactorWorkshopApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class TradeServiceImplTest {
	@MockBean
	private Receiver receiver;

	@MockBean
	private TradeRepository tradeRepository;

	@Autowired
	private TradeServiceImpl tradeService;

	private OrderExecution orderExecution = new OrderExecution(new BigDecimal(1000d), new BigDecimal(10d), Side.SELL);
	private Trade trade = new Trade(UUID.randomUUID().toString(), orderExecution, LocalDateTime.now());

	@Before
	public void init() throws JsonProcessingException {
		Delivery delivery = new Delivery(null, null,
				new ObjectMapper().findAndRegisterModules().writeValueAsBytes(orderExecution));

		Mockito.when(receiver.consumeAutoAck("order-execution.queue")).thenReturn(Flux.just(delivery));
		Mockito.when(tradeRepository.save(Mockito.any())).thenReturn(Mono.just(trade));
	}

	@Test
	public void test() {
		tradeService.initialize();

		Mockito.verify(tradeRepository).save(Mockito.argThat(this::assertTrade));
	}

	private boolean assertTrade(Trade trade) {
		Assert.assertEquals(trade.getOrderExecution().getAmount(), orderExecution.getAmount());
		Assert.assertEquals(trade.getOrderExecution().getPrice(), orderExecution.getPrice());
		Assert.assertEquals(trade.getOrderExecution().getSide(), orderExecution.getSide());
		Assert.assertNull(trade.getId());
		Assert.assertNotNull(trade.getTradeDate());

		return true;
	}
}
