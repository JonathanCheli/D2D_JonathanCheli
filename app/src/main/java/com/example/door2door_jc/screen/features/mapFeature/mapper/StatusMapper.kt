package com.example.door2door_jc.screen.features.mapFeature.mapper


import com.example.door2door_jc.data.BaseBookingMapper
import com.example.door2door_jc.data.BookingClosed
import com.example.door2door_jc.data.BookingOpened
import com.example.door2door_jc.data.Event
import com.example.door2door_jc.screen.features.mapFeature.model.BOOKING_CLOSED
import com.example.door2door_jc.screen.features.mapFeature.model.BOOKING_OPENED
import com.example.door2door_jc.screen.features.mapFeature.model.CLEAR
import com.example.door2door_jc.screen.features.mapFeature.model.StatusModel
import javax.inject.Inject

class StatusMapper @Inject constructor() : BaseBookingMapper<StatusModel> {
    override fun mapDataModelToViewModel(dataModel: Event): StatusModel {
        return when (dataModel) {
            is BookingOpened -> StatusModel(BOOKING_OPENED)
            is BookingClosed -> StatusModel(BOOKING_CLOSED)
            else -> StatusModel(CLEAR)
        }
    }
}