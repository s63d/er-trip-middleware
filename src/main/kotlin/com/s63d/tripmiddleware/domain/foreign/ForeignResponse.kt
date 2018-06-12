package com.s63d.tripmiddleware.domain.foreign

data class ForeignResponse(val id: String, val price: Double, val distance: Int, val vat:Int, val details: List<ForeignDetails>)