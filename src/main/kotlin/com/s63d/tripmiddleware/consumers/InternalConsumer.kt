package com.s63d.tripmiddleware.consumers

import com.s63d.tripmiddleware.domain.foreign.ForeignDetails
import com.s63d.tripmiddleware.domain.foreign.ForeignResponse
import com.s63d.tripmiddleware.domain.intern.InternResponse
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.Exchange
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.rabbit.annotation.QueueBinding
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import kotlin.math.roundToInt

@Component
class InternalConsumer(private val rabbitTemplate: RabbitTemplate) {

    @Value("\${VAT}")
    val VAT: Int = 0

    private val logger = LoggerFactory.getLogger(this::class.java)
    @RabbitListener(bindings = [QueueBinding(value = Queue("INTERN_AT_RESP"), key = ["resp"], exchange = Exchange("AT"))])
    fun handle(msg: InternResponse) {
        logger.info("[←] Received intern response")
        logger.info("    $msg")
        logger.info("")

        val foreignResponse = ForeignResponse(msg.id, msg.price, msg.distance.roundToInt(), VAT, listOf(ForeignDetails(msg.rate, "Vehicle has label ${msg.label}, ${msg.rate} * ${msg.distance} (rate x distance)", Long.MIN_VALUE, Long.MAX_VALUE)))
        val key = "rich_route_${msg.dest}"

        logger.info("[↑] Sent foreign response")
        logger.info("    $foreignResponse")
        logger.info("")
        rabbitTemplate.convertAndSend(key, foreignResponse)
    }

}