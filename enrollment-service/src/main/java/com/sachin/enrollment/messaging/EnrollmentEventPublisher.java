package com.sachin.enrollment.messaging;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class EnrollmentEventPublisher {

    private final AmqpTemplate amqpTemplate;

    @Value("${rabbitmq.enrollment.exchange}")
    private String exchange;

    @Value("${rabbitmq.enrollment.routing-key}")
    private String routingKey;

    public void sendEnrollmentNotification(String message) {
        amqpTemplate.convertAndSend(exchange, routingKey, message);
    }


}


