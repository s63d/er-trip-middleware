package com.s63d.tripmiddleware.domain

data class ForeignRequest(val id: String, val vehicleWeight: Int, val trips: List<List<ForeignRequestTripLocation>>, val origin: String)
data class ForeignRequestTripLocation(val timestamp: Long, val lat: Double, val lng: Double)
