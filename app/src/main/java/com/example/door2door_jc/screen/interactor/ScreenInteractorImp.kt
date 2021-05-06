package com.example.door2door_jc.screen.interactor

import com.example.door2door_jc.connection.BookingsWebSocket
import com.example.door2door_jc.data.BaseBookingMapper
import io.reactivex.Observable
import javax.inject.Inject

class ScreenInteractorImp @Inject constructor(
    private val bookingsWebSocket: BookingsWebSocket
) :
    ScreenInteractor {

    override fun <V> getVehicleLocationUpdates(
        bookingLocationMapper: BaseBookingMapper<V>
    ): Observable<V> {
        return bookingsWebSocket.getVehicleLocationUpdates()
            .map { bookingLocationMapper.mapDataModelToViewModel(it) }
    }

    override fun <V> getBookingStatusUpdates(
        bookingStatusMapper: BaseBookingMapper<V>): Observable<V> {
        return bookingsWebSocket.getStatusUpdates()
            .map { bookingStatusMapper.mapDataModelToViewModel(it) }
    }

    override fun <V> getStopLocationsUpdates(
        stopLocationsMapper: BaseBookingMapper<V>): Observable<V> {
        return bookingsWebSocket.getStopLocationUpdates()
            .map { stopLocationsMapper.mapDataModelToViewModel(it) }
    }

    override fun connectToWebSocket() {
        bookingsWebSocket.connectToWebSocket()
    }
}