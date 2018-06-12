package com.s63d.tripmiddleware.consumers

import com.s63d.tripmiddleware.domain.foreign.ForeignResponse
import com.s63d.tripmiddleware.domain.foreign.ForeignRequest
import com.s63d.tripmiddleware.domain.foreign.ForeignRequestTripLocation
import com.s63d.tripmiddleware.domain.intern.InternRequest
import com.s63d.tripmiddleware.utils.LocationUtils
import com.s63d.tripmiddleware.utils.LocationUtils.totalDistance
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component
import kotlin.math.roundToLong

@Component
class ForeignConsumer(private val rabbitTemplate: RabbitTemplate) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @RabbitListener(queues = ["foreign_route_AT"])
    fun handle(request: ForeignRequest) {
        logger.info("[↓] Received request from ${request.origin}")
        logger.info("    $request")
        logger.info("")

        request.trips.map {
            InternRequest(request.id, request.vehicleWeight, totalDistance(it), request.origin)
        }.forEach {
            rabbitTemplate.convertAndSend("AT", "req", it)
            logger.info("[→] Sent intern request for ${it.country}")
            logger.info("    $it")
            logger.info("")
        }
    }

    @RabbitListener(queues = ["rich_route_AT"])
    fun handle(response: ForeignResponse) {
        logger.info("[↓] Received response from ${response.origin}")
        logger.info("    $response")
        logger.info("")

        rabbitTemplate.convertAndSend("AT", "part", response)
        logger.info("[→] Sent intern part for ${response.origin}")
        logger.info("    $response")
        logger.info("")
    }
}