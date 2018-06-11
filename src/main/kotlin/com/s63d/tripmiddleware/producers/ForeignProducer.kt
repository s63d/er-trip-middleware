package com.s63d.tripmiddleware.producers

import com.s63d.tripmiddleware.domain.ForeignRequest
import com.s63d.tripmiddleware.domain.ForeignVehicle
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component
import java.util.*

@Component
class ForeignProducer(private val rabbitTemplate: RabbitTemplate) {

    fun doRequest(country: String = "be") {
        val v = ForeignVehicle(UUID.randomUUID().toString(), "A", 1000)
        val r = ForeignRequest(UUID.randomUUID().toString(), v, "etjbHmq_iAac@sjD|fBwu@pJ`NhvAsVjSpBd{Bse@xYiv@fiBuf@bcB{v@`QyJrB`gBm@f`@inB|[{eAf}@cwAzYolBd_@{oAfjCa}@ja@wP}U??kUlE", "AT")

        rabbitTemplate.convertAndSend("eu.req", country, r)

    }

}