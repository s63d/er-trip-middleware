package com.s63d.tripmiddleware.consumers

import com.s63d.tripmiddleware.domain.foreign.ForeignResponse
import com.s63d.tripmiddleware.domain.foreign.ForeignRequest
import com.s63d.tripmiddleware.domain.foreign.ForeignRequestTripLocation
import com.s63d.tripmiddleware.domain.intern.InternRequest
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
            InternRequest(request.id, request.vehicleWeight, computeDistance(it), request.origin)
        }.forEach {
            rabbitTemplate.convertAndSend("AT", "req", it)
            logger.info("[→] Sent intern request for ${it.country}")
            logger.info("    $it")
            logger.info("")
        }
    }


    fun computeDistance(locations: List<ForeignRequestTripLocation>) : Long {
        if(locations.size < 2)
            return 0L
        var sum = 0L
        for (i in 0 until locations.size - 1) {
            sum += distFrom(locations[i], locations[i + 1]).roundToLong()
        }
        return sum
    }

    private fun distFrom(loc1: ForeignRequestTripLocation, loc2: ForeignRequestTripLocation): Float {
        val earthRadius = 6371000.0 //meters
        val dLat = Math.toRadians(loc2.lat - loc1.lat)
        val dLng = Math.toRadians(loc2.lon - loc1.lon)
        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(loc1.lat)) * Math.cos(Math.toRadians(loc2.lat)) *
                Math.sin(dLng / 2) * Math.sin(dLng / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

        return (earthRadius * c).toFloat()
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