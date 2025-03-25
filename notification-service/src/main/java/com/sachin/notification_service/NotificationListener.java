package com.sachin.notification_service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void handleCourseNotification(String message) {
        System.out.println("Received course notification: " + message);
    }

    @RabbitListener(queues = "${rabbitmq.enrollment.queue}")
    public void handleEnrollmentNotifications(String message){
        System.out.println("Recieved enrollment notification: " + message);
    }
}
