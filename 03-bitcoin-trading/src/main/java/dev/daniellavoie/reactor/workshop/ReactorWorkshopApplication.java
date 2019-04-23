package dev.daniellavoie.reactor.workshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import dev.daniellavoie.reactor.workshop.trade.TradeService;

@SpringBootApplication
public class ReactorWorkshopApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReactorWorkshopApplication.class, args).getBean(TradeService.class).initialize();
	}
}
