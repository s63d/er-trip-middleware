package com.s63d.tripmiddleware

import com.s63d.tripmiddleware.domain.Trip
import com.s63d.tripmiddleware.domain.TripContainer
import com.s63d.tripmiddleware.domain.Vehicle
import com.s63d.tripmiddleware.producers.ForeignProducer
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component

@SpringBootApplication
class TripMiddlewareApplication

fun main(args: Array<String>) {
    runApplication<TripMiddlewareApplication>(*args)
}


@Component
class Tester(private val foreignProducer: ForeignProducer) : CommandLineRunner {
    override fun run(vararg args: String?) {

        val trip = Trip(1111, "etjbHmq_iAac@sjD|fBwu@pJ`NhvAsVjSpBd{Bse@xYiv@fiBuf@bcB{v@`QyJrB`gBm@f`@inB|[{eAf}@cwAzYolBd_@{oAfjCa}@ja@wP}U??kUlE", Vehicle('a', 1000))
        foreignProducer.doRequest(trip)
    }
}