package com.s63d.tripmiddleware.consumers

import com.s63d.tripmiddleware.domain.intern.ForeignRequest
import com.s63d.tripmiddleware.domain.intern.ForeignRequestTripLocation
import com.s63d.tripmiddleware.domain.intern.InternRequest
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class ForeignConsumer(private val rabbitTemplate: RabbitTemplate) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @RabbitListener(queues = ["foreign_route_AT"])
    fun handle(request: ForeignRequest) {
        logger.info("[↓] Received request from ${request.origin}")
        logger.info("    $request")
        logger.info("")

        request.trips.map {
            InternRequest(request.id, request.vehicleWeight, computeDistance(it), request.origin)
        }.forEach {
            rabbitTemplate.convertAndSend("AT", "req", it)
            logger.info("[→] Sent intern request")
            logger.info("    $it")
            logger.info("")
        }
    }


    fun computeDistance(tripLocation: List<ForeignRequestTripLocation>) : Double {

        return 10.0
    }
}