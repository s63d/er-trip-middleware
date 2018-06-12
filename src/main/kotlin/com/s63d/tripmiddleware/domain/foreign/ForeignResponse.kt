package com.s63d.tripmiddleware.domain.foreign

data class ForeignResponse(val id: String, val price: Double, val distance: Long, val vat:Int, val origin: String, val details: List<ForeignDetails>) {
    private constructor() : this("", 0.0, 0,0, "UNKNOWN", emptyList())
}