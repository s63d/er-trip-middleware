package com.s63d.tripmiddleware.utils

import com.s63d.tripmiddleware.domain.foreign.ForeignRequestTripLocation
import kotlin.math.roundToLong

object LocationUtils {
    fun totalDistance(locations: List<ForeignRequestTripLocation>) : Long {
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
        val dLng = Math.toRadians(loc2.lng - loc1.lng)
        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(loc1.lat)) * Math.cos(Math.toRadians(loc2.lat)) *
                Math.sin(dLng / 2) * Math.sin(dLng / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

        return (earthRadius * c).toFloat()
    }
}