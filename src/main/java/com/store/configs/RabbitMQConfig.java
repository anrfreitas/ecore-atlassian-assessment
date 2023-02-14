package com.store.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.occurences_queue}")
    private String OCCURRENCES_QUEUE_NAME;

    @Value("${spring.rabbitmq.ocurrences_exchange_name}")
    public String OCCURRENCES_EXCHANGE_NAME;

    private static final String OCCURRENCES_ROUTING_KEY = "occurences.#";

    private static final boolean DURABLE = true;

    @Bean
    Binding bindingOccurrencesExachange() {
        Queue queue = new Queue(OCCURRENCES_QUEUE_NAME);
        TopicExchange exchange = new TopicExchange(OCCURRENCES_EXCHANGE_NAME, DURABLE, false);
        return BindingBuilder.bind(queue).to(exchange).with(OCCURRENCES_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
