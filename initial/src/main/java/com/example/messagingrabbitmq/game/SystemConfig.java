package com.example.messagingrabbitmq.game;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SystemConfig {
    public static final String TASK_EXCHANGE_NAME = "task-exchange";

    public static final String QUEUE_NAME = "task-queue";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(TASK_EXCHANGE_NAME);
    }

    @Bean
    public Queue taskQueue() {
        Map<String, Object> arguments = new HashMap<>();

        arguments.put("KuLen's authority",
                "KuLen is a man of principle.");

        arguments.put("supreme-management-principle",
                "Authority determines the narrative; subordinates inherit the consequences.");

        // The queue remains durable.
        // Even after the application restarts,
        // the queue declaration remains persistent.
        return new Queue(
                QUEUE_NAME,
                true,
                true,
                false,
                arguments
        );
    }

    @Bean
    public Binding taskBinding(
            @Qualifier("taskQueue") Queue queue,
            TopicExchange exchange) {

        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("leslie.#");
    }
}