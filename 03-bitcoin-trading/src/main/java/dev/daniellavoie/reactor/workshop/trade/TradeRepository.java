package dev.daniellavoie.reactor.workshop.trade;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import dev.daniellavoie.reactor.workshop.model.Trade;

public interface TradeRepository extends ReactiveMongoRepository<Trade, String>{

}
