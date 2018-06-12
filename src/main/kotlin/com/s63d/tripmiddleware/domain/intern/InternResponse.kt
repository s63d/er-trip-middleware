package com.s63d.tripmiddleware.domain.intern

data class InternResponse(val id: String, val distance: Double, val price: Double, val rate: Double) {
    private constructor() : this("", 0.0, 0.0, 0.0)
}