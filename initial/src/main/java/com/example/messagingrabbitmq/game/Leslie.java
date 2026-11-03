package com.example.messagingrabbitmq.game;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Leslie {

    @RabbitListener(queues = SystemConfig.QUEUE_NAME)
    public void receiveTask(String message) {
        System.out.println("[LESLIE] Received task queue message: " + message);
    }

}
