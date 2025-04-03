	package com.sachin.notification_service;

	import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
	import org.springframework.boot.SpringApplication;
	import org.springframework.boot.autoconfigure.SpringBootApplication;
	import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
	import org.springframework.context.annotation.Bean;
	import org.springframework.amqp.support.converter.MessageConverter;
	import org.springframework.amqp.rabbit.connection.ConnectionFactory;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.amqp.core.Queue;
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.amqp.core.Binding;
	import org.springframework.amqp.core.BindingBuilder;
	import org.springframework.amqp.core.Queue;
	import org.springframework.amqp.core.TopicExchange;
	import org.springframework.beans.factory.annotation.Value;
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;


	@SpringBootApplication
	public class NotificationServiceApplication {

		public static void main(String[] args) {
			SpringApplication.run(NotificationServiceApplication.class, args);
		}




		@Configuration
		public class RabbitMQConfig {


			@Value("${rabbitmq.queue}")
			private String courseQueue;

			@Value("${rabbitmq.exchange}")
			private String courseExchange;

			@Value("${rabbitmq.routing-key}")
			private String courseRoutingKey;

			@Value("${rabbitmq.enrollment.queue}")
			private String enrollmentQueue;

			@Value("${rabbitmq.enrollment.exchange}")
			private String enrollmentExchange;

			@Value("${rabbitmq.enrollment.routing-key}")
			private String enrollmentRoutingKey;

			// Course Queue
			@Bean
			public Queue courseNotificationQueue() {
				return new Queue(courseQueue, true);
			}

			@Bean
			public TopicExchange courseTopicExchange() {
				return new TopicExchange(courseExchange);
			}

			@Bean
			public Binding courseBinding() {
				return BindingBuilder.bind(courseNotificationQueue())
						.to(courseTopicExchange())
						.with(courseRoutingKey);
			}

			// Enrollment Queue
			@Bean
			public Queue enrollmentNotificationQueue() {
				return new Queue(enrollmentQueue, true);
			}

			@Bean
			public TopicExchange enrollmentTopicExchange() {
				return new TopicExchange(enrollmentExchange);
			}

			@Bean
			public Binding enrollmentBinding() {
				return BindingBuilder.bind(enrollmentNotificationQueue())
						.to(enrollmentTopicExchange())
						.with(enrollmentRoutingKey);
			}
		}
	}




