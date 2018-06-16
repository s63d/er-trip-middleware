package com.s63d.tripmiddleware.domain.intern

data class InternTrip (val id: Long, val polyline: String, val vehicleWeight: Int) {
    private constructor() : this(-1, "", -1)
}