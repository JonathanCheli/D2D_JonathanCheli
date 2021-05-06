package com.example.door2door_jc.screen.interactor

import com.example.door2door_jc.data.BaseBookingMapper
import io.reactivex.Observable


interface ScreenInteractor {
    fun connectToWebSocket()

    fun <V> getVehicleLocationUpdates(
        bookingLocationMapper: BaseBookingMapper<V>): Observable<V>

    fun <V> getBookingStatusUpdates(
        bookingStatusMapper: BaseBookingMapper<V>): Observable<V>

    fun <V> getStopLocationsUpdates(
        stopLocationsMapper: BaseBookingMapper<V>): Observable<V>
}