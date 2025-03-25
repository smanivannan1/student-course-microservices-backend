	package com.sachin.notification_service;

	import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
	import org.springframework.boot.SpringApplication;
	import org.springframework.boot.autoconfigure.SpringBootApplication;
	import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
	import org.springframework.context.annotation.Bean;
	import org.springframework.amqp.support.converter.MessageConverter;
	import org.springframework.amqp.rabbit.connection.ConnectionFactory;


	@SpringBootApplication
	public class NotificationServiceApplication {

		public static void main(String[] args) {
			SpringApplication.run(NotificationServiceApplication.class, args);
		}
	}
