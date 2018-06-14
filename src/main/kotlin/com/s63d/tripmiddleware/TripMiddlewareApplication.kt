package com.s63d.tripmiddleware

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TripMiddlewareApplication

fun main(args: Array<String>) {
    runApplication<TripMiddlewareApplication>(*args)
}


//@Component
//@EnableScheduling
//class Tester(private val foreignProducer: ForeignProducer) {
//    //var id:Long = 0xC0FFEE
//
//    val polylines = listOf(
//            "}`eqHezrc@m|Ase@_Uw_CfRcwBdtAy}Cf]adCfiAytAfs@euCvAuhEd\\i{L`oDy~UvmKibWdrZmcWfsMehM`~O_e_@djPxwu@~{Dd}b@jrMlnMxpSytAdjU_{Kx~LmjZkUyy~@cfNmlrBr_\\_yhE|f|EskgU{sFcazAldFgm`A`zXsb^t{e@ikSzd[|yh@bxi@~}[rvWgr}Bqaa@m}mCoyYsxJ"
//           //,"etjbHmq_iAac@sjD|fBwu@pJ`NhvAsVjSpBd{Bse@xYiv@fiBuf@bcB{v@`QyJrB`gBm@f`@inB|[{eAf}@cwAzYolBd_@{oAfjCa}@ja@wP}U??kUlE"
//    )
//    var id:Long = 1
//    //@Scheduled(fixedDelay = 5 * 1_000)
//    fun run() {
//        val trip = Trip(id, polylines.random(), Vehicle('a', 1000))
//        foreignProducer.processTrip(trip)
//        id += 1
//
//        //val trip = Trip(1111, , Vehicle('a', 1000))
//    }
//}

//private fun <E> List<E>.random() = this[Random().nextInt(this.size)]


