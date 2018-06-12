package com.s63d.tripmiddleware.utils

import com.s63d.tripmiddleware.domain.Trip
import com.s63d.tripmiddleware.domain.TripContainer
import org.springframework.stereotype.Service
import com.vividsolutions.jts.geom.*
import it.rambow.master.javautils.PolylineEncoder.createEncodings
import it.rambow.master.javautils.Track
import it.rambow.master.javautils.Trackpoint
import org.scoutant.polyline.PolylineDecoder
import org.slf4j.LoggerFactory
import org.wololo.geojson.FeatureCollection
import org.wololo.geojson.GeoJSONFactory
import org.wololo.jts2geojson.GeoJSONReader
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import javax.annotation.PostConstruct

@Service
class TripSplitter {
    companion object {
        lateinit var geometries: List<Geometry>
        lateinit var countryMap: Map<String, String>
        private val logger by lazy { LoggerFactory.getLogger(this::class.java)!! }
        val decoder = PolylineDecoder()
        val factory =  GeometryFactory(PrecisionModel(PrecisionModel.FLOATING))
    }

    @PostConstruct
    fun init() {
        logger.info("Loading geometries..")
        val path = Paths.get(javaClass.getResource("/countries.geojson").toURI())
        val bytes = Files.readAllBytes(path)
        val json = String(bytes)
        val rawCountries = GeoJSONFactory.create(json) as FeatureCollection
        val reader = GeoJSONReader()
        geometries = rawCountries.features.map {

            val geo = reader.read(it.geometry)
            geo.userData = it.properties
            return@map geo.also { _ ->
                logger.debug("Loaded ${it.properties["ADMIN"]} (${it.properties["ISO_A3"]})")
            }
        }
        logger.info("Loaded ${geometries.size} geometries!")
        logger.info("Loading country map..")
        countryMap = Locale.getISOCountries().map {
            Locale("", it).isO3Country to it
        }.toMap()
        logger.info("Done constructing country map")
    }

    fun decodePolyline(polyline: String) = decoder.decode(polyline)
            .map {
                factory.createPoint(Coordinate(it.lng, it.lat))
            }

    private fun encodePoints(points: Iterable<Point>) : String {
        val track = Track()

        track.trackpoints = ArrayList(points.map {p ->
            Trackpoint(p.y, p.x)
        })

        return createEncodings(track, 17, 1)["encodedPoints"].toString()
    }

    private fun resolveCode(p: Point) : String {
        val iso3 = (TripSplitter.geometries.find { it.contains(p) }?.userData as Map<*, *>?)?.get("ISO_A3") as String?
        if(iso3 != null) {
           return countryMap[iso3] ?: "--"
        }
        return "--"
    }


    fun splitTrip(trip: Trip) : List<TripContainer> {
        val points = decodePolyline(trip.polyline)
        val result = mutableListOf<Pair<String, MutableList<Point>>>()
        for (point in points) {
            val code = resolveCode(point)
            if(result.lastOrNull()?.first != code)
                result.add(code to mutableListOf(point))
        }
        return result.map { TripContainer(it.first, it.second) }
    }

}