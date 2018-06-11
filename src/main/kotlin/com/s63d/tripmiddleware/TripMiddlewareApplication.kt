package com.s63d.tripmiddleware

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
        foreignProducer.doRequest()
    }
}