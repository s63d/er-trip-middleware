package com.s63d.tripmiddleware.domain.foreign

data class ForeignRequestTripLocation(val timestamp: Long, val lat: Double, val lng: Double) {
    private constructor() : this(0L, 0.0, 0.0)
}