package com.example.door2door_jc.screen.features.rideUpdates.model

data class BookingStatusModel(val status: String,
                              val pickupAddress: String? = null,
                              val dropOffAddress: String? = null,
                              val isBookingClosed: Boolean = false)
