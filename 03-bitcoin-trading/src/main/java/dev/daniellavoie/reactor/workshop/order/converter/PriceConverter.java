package dev.daniellavoie.reactor.workshop.order.converter;

import java.math.BigDecimal;

import dev.daniellavoie.reactor.workshop.model.OrderBookEntry;
import dev.daniellavoie.reactor.workshop.model.Side;

public abstract class PriceConverter {
	public static OrderBookEntry convertToOrderBookEntry(String[] price, Side side) {
		return new OrderBookEntry(new BigDecimal(price[0]), new BigDecimal(price[1]), side);
	}
}
