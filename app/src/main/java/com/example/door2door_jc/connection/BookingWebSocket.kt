package com.example.door2door_jc.connection




import com.example.door2door_jc.data.Event
import io.reactivex.Observable

interface BookingsWebSocket {

    fun connectToWebSocket()

    fun getStatusUpdates(): Observable<Event>

    fun getVehicleLocationUpdates(): Observable<Event>

    fun getStopLocationUpdates(): Observable<Event>
}