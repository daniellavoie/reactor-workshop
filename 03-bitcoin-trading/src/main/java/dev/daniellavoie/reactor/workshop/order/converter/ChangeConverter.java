package dev.daniellavoie.reactor.workshop.order.converter;

import java.math.BigDecimal;

import dev.daniellavoie.reactor.workshop.model.OrderBookEntry;
import dev.daniellavoie.reactor.workshop.model.Side;

public abstract class ChangeConverter {
	public static OrderBookEntry convertToOrderBookEntry(String[] price) {
		return new OrderBookEntry(new BigDecimal(price[1]), new BigDecimal(price[2]),
				price[0].equals("buy") ? Side.BUY : Side.SELL);
	}
}
