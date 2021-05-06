package com.example.door2door_jc.screen.features.rideUpdates.view

interface RideUpdatesView {
    fun updateStartEndAddresses(pickupAddress: String, dropOffAddress: String)
    fun updateStatus(status: String, bookingClosed: Boolean)
    fun updateNextStopAddress(nextStopAddress: String)
    fun toggleNextStopAddressVisibility(status: String)
}