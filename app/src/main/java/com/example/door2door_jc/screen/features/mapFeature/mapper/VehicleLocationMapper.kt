package com.example.door2door_jc.screen.features.mapFeature.mapper

import com.example.door2door_jc.screen.features.mapFeature.model.VehicleLocationModel
import com.example.door2door_jc.data.BaseBookingMapper
import com.example.door2door_jc.data.Event
import com.example.door2door_jc.data.VehicleLocationUpdated
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class VehicleLocationMapper @Inject constructor() : BaseBookingMapper<VehicleLocationModel> {

    override fun mapDataModelToViewModel(dataModel: Event): VehicleLocationModel {
        val vehicleLocationUpdated = dataModel as VehicleLocationUpdated
        val currentLat = vehicleLocationUpdated.data.lat
        val currentLng = vehicleLocationUpdated.data.lng
        return VehicleLocationModel(LatLng(currentLat, currentLng))
    }
}