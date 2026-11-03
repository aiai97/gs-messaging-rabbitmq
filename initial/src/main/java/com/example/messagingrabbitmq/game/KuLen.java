package com.example.messagingrabbitmq.game;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class KuLen implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;

    public KuLen(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("dispatching tasks... ");
        rabbitTemplate.convertAndSend(SystemConfig.TASK_EXCHANGE_NAME, "leslie.task", "A special task for Leslie from KuLen!");
    }
}
