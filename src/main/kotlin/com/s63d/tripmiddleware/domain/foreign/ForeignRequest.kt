package com.s63d.tripmiddleware.domain.foreign

data class ForeignRequest(val id: String, val vehicleWeight: Int, val trips: List<List<ForeignRequestTripLocation>>, val origin: String) {
    private constructor(): this("",0, emptyList(), "")
}