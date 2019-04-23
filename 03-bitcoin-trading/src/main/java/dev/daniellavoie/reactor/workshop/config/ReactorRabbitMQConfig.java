package dev.daniellavoie.reactor.workshop.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.Address;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import reactor.core.scheduler.Schedulers;
import reactor.rabbitmq.RabbitFlux;
import reactor.rabbitmq.Receiver;
import reactor.rabbitmq.ReceiverOptions;
import reactor.rabbitmq.Sender;
import reactor.rabbitmq.SenderOptions;
import reactor.rabbitmq.Utils;

@Configuration
public class ReactorRabbitMQConfig {

	@Bean
	public ConnectionFactory rabbitMQconnectionFactory() {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.useNio();

		return connectionFactory;
	}

	@Bean
	public Utils.ExceptionFunction<ConnectionFactory, ? extends Connection> connectionSupplier(
			@Value("${rabbitmq.hosts:localhost}") String hosts,
			@Value("${spring.application.name:reactive-workshop}") String clientName) {
		return cf -> cf.newConnection(Arrays.stream(hosts.split(",")).map(Address::new).toArray(Address[]::new));
	}

	@Bean
	public Receiver receiver(ConnectionFactory rabbitMQconnectionFactory,
			Utils.ExceptionFunction<ConnectionFactory, ? extends Connection> connectionSupplier) {
		ReceiverOptions receiverOptions = new ReceiverOptions().connectionFactory(rabbitMQconnectionFactory)
				.connectionSupplier(connectionSupplier).connectionSubscriptionScheduler(Schedulers.elastic());

		return RabbitFlux.createReceiver(receiverOptions);
	}

	@Bean
	public Sender sender(ConnectionFactory rabbitMQconnectionFactory,
			Utils.ExceptionFunction<ConnectionFactory, ? extends Connection> connectionSupplier) {
		SenderOptions senderOptions = new SenderOptions().connectionFactory(rabbitMQconnectionFactory)
				.connectionSupplier(connectionSupplier).resourceManagementScheduler(Schedulers.elastic());

		return RabbitFlux.createSender(senderOptions);
	}
}
