package com.example.messagingrabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MessagingRabbitmqApplication {

	static final String topicExchangeName = "spring-boot-exchange";

	static final String queueName = "spring-boot";

	static final String secondQueueName = "spring-boot-leslie";

	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	@Bean
	Queue LeslieQueue() {
		return new Queue(secondQueueName, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(topicExchangeName);
	}

	@Bean
	Binding binding(@Qualifier("queue") Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
	}

	@Bean
	Binding secondBinding(@Qualifier("LeslieQueue") Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("leslie.#");
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName, secondQueueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	SimpleMessageListenerContainer secondContainer(
			ConnectionFactory connectionFactory,
			@Qualifier("secondListenerAdapter") MessageListenerAdapter adapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(secondQueueName);
		container.setMessageListener(adapter);
		return container;
	}

	@Bean
	MessageListenerAdapter secondListenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessageFromLeslie");
	}

	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	public static void main(String[] args) throws InterruptedException {
//		SpringApplication.run(MessagingRabbitmqApplication.class, args).close();
		SpringApplication.run(MessagingRabbitmqApplication.class, args);
	}

}
