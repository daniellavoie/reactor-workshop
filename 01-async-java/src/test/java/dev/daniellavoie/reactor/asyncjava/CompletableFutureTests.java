package dev.daniellavoie.reactor.asyncjava;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompletableFutureTests {
	private static final Logger LOGGER = LoggerFactory.getLogger(CompletableFutureTests.class);

	@Test
	public void simpleCompletableFuture() throws InterruptedException, ExecutionException {
		CompletableFuture<String> future = futureHello();

		Assert.assertEquals("hello", future.get());

		CompletableFuture<String> transformedHello = futureHello()
				.thenApply(hello -> "A synchronously transformed " + hello);

		Assert.assertEquals("A synchronously transformed hello", transformedHello.get());

		CompletableFuture<String> synchronouslyTransformedHello = futureHello()
				.thenCompose(s -> CompletableFuture.supplyAsync(() -> "An asynchronously transformed " + s, Executors.newFixedThreadPool(1)));

		Assert.assertEquals("An asynchronously transformed hello", synchronouslyTransformedHello.get());

	}

	private CompletableFuture<String> futureHello() {
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
			LOGGER.info("Completable future is being executed.");

			return "hello";
		});

		LOGGER.info("Completable future is declared.");

		return hello;
	}
}
