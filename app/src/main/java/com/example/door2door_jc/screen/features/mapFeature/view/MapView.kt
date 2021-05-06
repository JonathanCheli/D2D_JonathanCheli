package com.example.door2door_jc.screen.features.mapFeature.view

import com.google.android.gms.maps.model.LatLng

interface MapView {

    fun obtainGoogleMap()


    fun clearMap()

    fun updateVehicleMarker(finalLatLng: LatLng)

    fun showStartEndMarkers(pickupLatLng: LatLng,
                            dropOffLatLng: LatLng,intermediateStopLatLng: List<LatLng>)

    fun updateStopsMarkers(intermediateStopLatLng: List<LatLng>)

    fun clearAllMarkers()













}