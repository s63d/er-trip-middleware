package com.s63d.tripmiddleware.config

import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitConfig {

    @Bean
    fun jsonRabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
        val template = RabbitTemplate(connectionFactory)
        template.messageConverter = jsonConverter()
        return template
    }

    @Bean
    fun jsonConverter() = Jackson2JsonMessageConverter()

    @Bean
    fun richRouteAT() = Queue("rich_route_AT", true)

    @Bean
    fun foreignRouteAT() = Queue("foreign_route_AT", true)

    @Bean
    fun internalExchange() = DirectExchange("AT")
}