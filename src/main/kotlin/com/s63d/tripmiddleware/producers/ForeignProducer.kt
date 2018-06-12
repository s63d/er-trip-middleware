package com.s63d.tripmiddleware.producers

import com.s63d.tripmiddleware.domain.ForeignRequest
import com.s63d.tripmiddleware.domain.ForeignRequestTripLocation
import com.s63d.tripmiddleware.domain.Trip
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

    fun doRequest(completeTrip: Trip) {

        val trips = tripSplitter.splitTrip(completeTrip)
                .filter { it.country != COUNTRY_CODE }.groupBy { it.country }


        for(trip in trips) {
            val dest = trip.key
            val req = ForeignRequest(completeTrip.id.toString(), completeTrip.vehicle.weight, trip.value.map {
                it.locations.map {
                    ForeignRequestTripLocation(Long.MIN_VALUE, it.y, it.x)
                }
            }, COUNTRY_CODE)


            val key = "test_foreign_route_$dest"
            logger.info("Sending request to $dest ($key)")
            logger.info(req.toString())
            rabbitTemplate.convertAndSend(key, req)
        }
    }

}