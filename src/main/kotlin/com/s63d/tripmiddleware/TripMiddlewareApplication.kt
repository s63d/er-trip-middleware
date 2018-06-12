package com.s63d.tripmiddleware

import com.s63d.tripmiddleware.domain.Trip
import com.s63d.tripmiddleware.domain.Vehicle
import com.s63d.tripmiddleware.producers.ForeignProducer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@SpringBootApplication
class TripMiddlewareApplication

fun main(args: Array<String>) {
    runApplication<TripMiddlewareApplication>(*args)
}


@Component
@EnableScheduling
class Tester(private val foreignProducer: ForeignProducer) {
    //var id:Long = 0xC0FFEE

    @Scheduled(fixedDelay = 5 * 1_000)
    fun run() {
        val trip = Trip(133, "}`eqHezrc@m|Ase@_Uw_CfRcwBdtAy}Cf]adCfiAytAfs@euCvAuhEd\\i{L`oDy~UvmKibWdrZmcWfsMehM`~O_e_@djPxwu@~{Dd}b@jrMlnMxpSytAdjU_{Kx~LmjZkUyy~@cfNmlrBr_\\_yhE|f|EskgU{sFcazAldFgm`A`zXsb^t{e@ikSzd[|yh@bxi@~}[rvWgr}Bqaa@m}mCoyYsxJ", Vehicle('a', 1000))
        foreignProducer.doRequest(trip)
        //id += 1

        //val trip = Trip(1111, "etjbHmq_iAac@sjD|fBwu@pJ`NhvAsVjSpBd{Bse@xYiv@fiBuf@bcB{v@`QyJrB`gBm@f`@inB|[{eAf}@cwAzYolBd_@{oAfjCa}@ja@wP}U??kUlE", Vehicle('a', 1000))
    }
}


