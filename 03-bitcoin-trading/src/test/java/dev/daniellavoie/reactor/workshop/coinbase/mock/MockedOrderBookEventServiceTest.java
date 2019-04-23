package dev.daniellavoie.reactor.workshop.coinbase.mock;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import dev.daniellavoie.reactor.workshop.coinbase.model.ProductId;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "coinbase.mock.enabled=true")
public class MockedOrderBookEventServiceTest {
	@Autowired
	private MockedOrderBookEventService orderBookEventService;

	@Test
	public void mockWorks() {
		Assert.assertEquals(236,
				orderBookEventService.getEvents(new ProductId[] { ProductId.ETH_USD, ProductId.ETH_EUR }).collectList().block().size());
	}
}
