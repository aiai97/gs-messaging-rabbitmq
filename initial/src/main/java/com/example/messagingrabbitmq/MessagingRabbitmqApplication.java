package com.example.messagingrabbitmq;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit // enables Spring's annotation-driven RabbitMQ message listener infrastructure,
// allowing Spring to detect and process methods annotated with @RabbitListener.
public class MessagingRabbitmqApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessagingRabbitmqApplication.class, args);
	}
}
