package com.s63d.tripmiddleware.domain

data class ForeignRequest(val id: String, val vehicle: ForeignVehicle, val trip: String, val origin: String)

