package com.s63d.tripmiddleware.domain

import com.vividsolutions.jts.geom.Point

data class TripContainer(val country: String, val locations: List<Point>)