package com.s63d.tripmiddleware.consumers

import com.s63d.tripmiddleware.domain.foreign.ForeignDetails
import com.s63d.tripmiddleware.domain.foreign.ForeignResponse
import com.s63d.tripmiddleware.domain.intern.InternResponse
import com.s63d.tripmiddleware.domain.intern.InternTrip
import com.s63d.tripmiddleware.producers.ForeignProducer
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.Exchange
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.rabbit.annotation.QueueBinding
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class InternalConsumer(private val rabbitTemplate: RabbitTemplate, private val foreignProducer: ForeignProducer) {

    @Value("\${VAT}")
    val VAT: Int = 0

    @Value("\${country}")
    lateinit var COUNTRY: String

    private val logger = LoggerFactory.getLogger(this::class.java)
    @RabbitListener(bindings = [QueueBinding(value = Queue("INTERN_AT_RESP"), key = ["resp"], exchange = Exchange("AT"))])
    fun handle(msg: InternResponse) {
        logger.info("[←] Received intern response")
        logger.info("    $msg")
        logger.info("")

        val foreignResponse = ForeignResponse(msg.id, msg.price, msg.distance, VAT, COUNTRY, listOf(ForeignDetails(msg.rate, "Vehicle has label ${msg.label}, ${msg.rate} * ${msg.distance} (rate x distance)", Long.MIN_VALUE, Long.MAX_VALUE)))
        val key = "rich_route_${msg.dest}"

        logger.info("[↑] Sent foreign response to ${msg.dest}")
        logger.info("    $foreignResponse")
        logger.info("")
        rabbitTemplate.convertAndSend(key, foreignResponse)
    }


    @RabbitListener(bindings = [QueueBinding(value = Queue("INTERN_AT_COMPLETE_TRIP"), key = ["trip"], exchange = Exchange("AT"))])
    fun handle(msg: InternTrip) {
        logger.info("[←] Received intern complete trip #${msg.id}")
        logger.info("    $msg")
        logger.info("")
        foreignProducer.processTrip(msg)

        logger.info("[*] Sent requests for trip #${msg.id}")
        logger.info("")
    }
}