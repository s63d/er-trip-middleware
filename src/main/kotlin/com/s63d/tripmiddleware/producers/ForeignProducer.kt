package com.s63d.tripmiddleware.producers

import com.s63d.tripmiddleware.domain.foreign.ForeignRequest
import com.s63d.tripmiddleware.domain.foreign.ForeignRequestTripLocation
import com.s63d.tripmiddleware.domain.Trip
import com.s63d.tripmiddleware.domain.intern.InternTrip
import com.s63d.tripmiddleware.utils.TripSplitter
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ForeignProducer(private val rabbitTemplate: RabbitTemplate, private val tripSplitter: TripSplitter) {

    val logger = LoggerFactory.getLogger(this::class.java)

    @Value("\${country}")
    lateinit var COUNTRY_CODE: String

    fun processTrip(completeTrip: InternTrip) {
        val trips = tripSplitter.splitTrip(completeTrip).groupBy { it.country }

        for(trip in trips) {
            val dest = trip.key
            val req = ForeignRequest(completeTrip.id.toString(), completeTrip.vehicleWeight, trip.value.map {
                it.locations.map {
                    ForeignRequestTripLocation(Long.MIN_VALUE, it.y, it.x)
                }
            }, COUNTRY_CODE)


            val key = "foreign_route_$dest"
            logger.info("[↑] Sending request to $dest")
            logger.info("    $req")
            logger.info("")

            rabbitTemplate.convertAndSend(key, req)
        }

    }

}