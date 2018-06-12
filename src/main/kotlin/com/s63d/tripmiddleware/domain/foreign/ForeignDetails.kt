package com.s63d.tripmiddleware.domain.foreign

data class ForeignDetails(val rate: Double, val description: String, val start: Long, val end: Long) {
    private constructor() : this(0.0, "", 0L, 0L)
}