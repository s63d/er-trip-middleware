package com.s63d.tripmiddleware.producers

import com.s63d.tripmiddleware.domain.ForeignRequest
import com.s63d.tripmiddleware.domain.ForeignVehicle
import com.s63d.tripmiddleware.domain.Trip
import com.s63d.tripmiddleware.utils.TripSplitter
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component
import java.util.*

@Component
class ForeignProducer(private val rabbitTemplate: RabbitTemplate, private val tripSplitter: TripSplitter) {

    fun doRequest(trip: Trip) {
        val v = ForeignVehicle(trip.vehicle.rate,trip.vehicle.weight)

        tripSplitter.splitTrip(trip).filter { it.country != "AT" }.forEach {
            val req = ForeignRequest(UUID.randomUUID().toString(), v, it.polyline, "AT")
            rabbitTemplate.convertAndSend("eu.req", it.country, req)
        }

    }

}