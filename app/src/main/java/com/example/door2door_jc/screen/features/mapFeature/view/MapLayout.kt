package com.example.door2door_jc.screen.features.mapFeature.view

import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.location.Location
import android.util.AttributeSet
import android.util.Property
import android.view.LayoutInflater
import android.view.animation.LinearInterpolator
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.door2door_jc.R
import com.example.door2door_jc.screen.features.mapFeature.dagger.DaggerMapComponent
import com.example.door2door_jc.screen.features.mapFeature.dagger.MapModule
import com.example.door2door_jc.screen.features.mapFeature.presenter.*
import com.example.door2door_jc.screen.features.mapFeature.view.*
import com.example.door2door_jc.screen.view.ScreenActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.model.*
import com.google.maps.android.SphericalUtil
import kotlinx.android.synthetic.main.feature_map.view.*
import java.util.*
import javax.inject.Inject



private const val MARKER_TITLE_INTERMEDIATE_STOP = "Door2Door Stop"
private const val MARKER_ANIMATION_DURATION = 1000L
private const val VEHICLE_MARKER_ANCHOR = 0.5f
private const val pickup_drawable = R.drawable.ic_pickup_location_red
private const val dropoff_drawable = R.drawable.ic_dropoff_location_blue
private const val stops_drawable = R.drawable.stop_loccation_black

class MapLayout : MapView, RelativeLayout, OnMarkerClickListener {

    private var line: Polyline? = null

    @Inject
    lateinit var mapPresenter: MapPresenter
    private var googleMap: GoogleMap? = null
    private var markerPositionAnimator: ObjectAnimator? = null
    private var markerRotationAnimator: ObjectAnimator? = null
    private var vehicleMarker: Marker? = null
    private var pickupMarker: Marker? = null
    private var dropOffMarker: Marker? = null
    private var intermediateStopsMarkers = mutableMapOf<LatLng, Marker?>()




    constructor(context: Context) : super(context) {
        setUp(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setUp(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
            context, attrs,
            defStyleAttr
    ) {
        setUp(context)
    }

    private fun setUp(context: Context) {

        LayoutInflater.from(context).inflate(R.layout.feature_map, this, true)

        injectDependencies()
        mapPresenter.viewAttached()


    }

    private fun injectDependencies() {
        DaggerMapComponent.builder()
                .screenComponent((context as ScreenActivity).screenComponent)
                .mapModule(MapModule(this))
                .build()
                .injectMapView(this)
    }


    override fun obtainGoogleMap() {
                mapView.getMapAsync {
                googleMap = it
                mapPresenter.mapLoaded()





                }



    }

    override fun clearMap() {
        clearAllMarkers()
        googleMap?.clear()



    }

    /**
     *    Functions for displaying the vehicle and animating it
     */

    private fun initializeVehicleMarker(finalLatLng: LatLng) {
        showVehicleMarker(finalLatLng)
        moveCamera(finalLatLng)


    }

    private fun showVehicleMarker(finalLatLng: LatLng) {

        vehicleMarker = googleMap?.addMarker(
                MarkerOptions()
                        .position(finalLatLng)
                        .anchor(VEHICLE_MARKER_ANCHOR, VEHICLE_MARKER_ANCHOR)

        )
        vehicleMarker!!.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_car))





    }

    override fun updateVehicleMarker(finalLatLng: LatLng) {
        if (vehicleMarker == null) initializeVehicleMarker(finalLatLng)

        animateMarker(vehicleMarker, finalLatLng)
    }

    private fun moveCamera(finalLatLng: LatLng) {


        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(finalLatLng))




    }

    private fun animateMarker(marker: Marker?, finalLatLng: LatLng) {
        marker?.let {
            animateMarkerPosition(marker, finalLatLng)
            animateMarkerRotation(marker, finalLatLng)

        }
    }


    private fun animateMarkerPosition(marker: Marker, finalLatLng: LatLng) {
        val interpolator = LinearInterpolator()
        val typeEvaluator = TypeEvaluator<LatLng> { fraction, fromLatLng, toLatLng ->
            val interpolation = interpolator.getInterpolation(fraction)

            val lat = (toLatLng.latitude - fromLatLng.latitude) * interpolation + fromLatLng.latitude
            val lng = (toLatLng.longitude - fromLatLng.longitude) * interpolation + fromLatLng.longitude




            /**
             *    this Polyline works on tracing the path from the beginning to the end, throughout the whole ride.
             *    - Tracing the path, as long as the vehicle is moving -
             */
            line = googleMap!!.addPolyline(
                    PolylineOptions()
                            .add(fromLatLng, toLatLng)
                            .geodesic(true)
                            .zIndex(5f)
                            .width(5f)
                            .color(Color.BLACK)
            )


            return@TypeEvaluator LatLng(lat, lng)



        }
        val property = Property.of(Marker::class.java, LatLng::class.java, "position")
        markerPositionAnimator?.cancel()
        markerPositionAnimator =
                ObjectAnimator.ofObject(marker, property, typeEvaluator, finalLatLng)
        markerPositionAnimator?.duration = MARKER_ANIMATION_DURATION
        markerPositionAnimator?.start()



    }

    private fun animateMarkerRotation(marker: Marker, finalLatLng: LatLng) {
        val initialLocation = marker.position.convertToLocation()
        val finalLocation = finalLatLng.convertToLocation()


        val newRotation = initialLocation?.bearingTo(finalLocation)


        newRotation?.let {
            val rotationProperty = Property.of(Marker::class.java, Float::class.java, "rotation")
            markerRotationAnimator?.cancel()
            markerRotationAnimator = ObjectAnimator.ofFloat(marker, rotationProperty, it)
            markerRotationAnimator?.duration = MARKER_ANIMATION_DURATION
            markerRotationAnimator?.start()



        }


    }

    private fun LatLng?.convertToLocation(): Location? = this?.let {
        val location = Location("")
        location.latitude = latitude
        location.longitude = longitude



        return location




    }

    /**
     * Markers at the different location
     */


    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(
                0,
                0,
                vectorDrawable.intrinsicWidth,
                vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
                vectorDrawable.intrinsicWidth,
                vectorDrawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun showStartEndMarkers(
            pickupLatLng: LatLng,
            dropOffLatLng: LatLng, intermediateStopLatLng: List<LatLng>
    ) {
        pickupMarker = showMarkerWithDrawable(pickupLatLng, pickup_drawable)
        dropOffMarker = showMarkerWithDrawable(dropOffLatLng, dropoff_drawable)


        showCurvedPolyline(pickupLatLng, dropOffLatLng, 1.5)


    }


    /**
     *    this Polyline works on tracing the path from the pickupLocation to dropoff location.
     *    Using components of import com.google.maps.android.SphericalUtil, to create a Spherical Polyline,
     *    Uber style.
     */

    private fun showCurvedPolyline(pickupLatLng: LatLng, dropOffLatLng: LatLng, k: Double) {
        val d : Double = SphericalUtil.computeDistanceBetween(pickupLatLng, dropOffLatLng)
        val h : Double = SphericalUtil.computeHeading(pickupLatLng, dropOffLatLng)

        //Midpoint position
        val p : LatLng = SphericalUtil.computeOffset(pickupLatLng, d * 0.5, h)

        //Apply some mathematics to calculate position of the circle center
        val x : Double = (1-k*k)*d*0.5/(2*k)
        val r : Double = (1+k*k)*d*0.5/(2*k)

        val c : LatLng =  SphericalUtil.computeOffset(p, x, h + 85.0)

        //Polyline options
        val options = PolylineOptions()
        val pattern = Arrays.asList<PatternItem>(Dash(10f), Gap(10f))


        //Calculate heading between circle center and two points
        val h1 : Double = SphericalUtil.computeHeading(c, pickupLatLng)
        val h2 : Double = SphericalUtil.computeHeading(c, dropOffLatLng)

        //Calculate positions of points on circle border and add them to polyline options

        val numpoints = 1000
        val step: Double = (h2 - h1) / numpoints
        for (i in 0 until numpoints)
        {
            val pi = SphericalUtil.computeOffset(c, r, h1 + i * step)
            options.add(pi)
        }

        //Draw polyline
        googleMap!!.addPolyline(
                options.width(10f).color(Color.RED).geodesic(false).pattern(pattern)
        )
    }




    override fun updateStopsMarkers(intermediateStopLatLng: List<LatLng>) {
        intermediateStopLatLng.forEach {
            var newMarker = intermediateStopsMarkers[it]
            if (newMarker == null) {
                newMarker = showMarkerWithDrawable(it, stops_drawable)
                intermediateStopsMarkers[it] = newMarker

            }

        }
    }

    private fun showMarkerWithDrawable(latLng: LatLng, drawable: Int): Marker? {
        val marker: Marker? = googleMap?.addMarker(
                MarkerOptions()
                        .position(latLng)
                        .title(MARKER_TITLE_INTERMEDIATE_STOP)
                        .snippet("Worth seeing! Click here for more info!")


        )

        marker!!.tag = 0
        googleMap!!.setOnMarkerClickListener(this)
        marker.setIcon(bitmapDescriptorFromVector(context, drawable))


        return marker

    }


    override fun onMarkerClick(marker: Marker): Boolean {

        Toast.makeText(context, "Door2Door thank you for this amazing oportunity!! ",
                Toast.LENGTH_SHORT).show()

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false


    }


    override fun clearAllMarkers() {
        pickupMarker?.remove()
        dropOffMarker?.remove()
        vehicleMarker?.remove()
        googleMap?.clear()


        intermediateStopsMarkers.forEach { (latLng, _) ->
            intermediateStopsMarkers[latLng]?.remove()
        }
        intermediateStopsMarkers.clear()
    }


}


