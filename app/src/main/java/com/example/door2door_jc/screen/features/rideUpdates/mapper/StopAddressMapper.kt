package com.example.door2door_jc.screen.features.rideUpdates.mapper

import com.example.door2door_jc.screen.features.rideUpdates.model.CLEAR
import com.example.door2door_jc.screen.features.rideUpdates.model.LAST_STOP
import com.example.door2door_jc.screen.features.rideUpdates.model.StopAddressModel
import com.example.door2door_jc.data.*
import javax.inject.Inject

class StopAddressMapper @Inject constructor() : BaseBookingMapper<StopAddressModel> {
    override fun mapDataModelToViewModel(dataModel: Event): StopAddressModel {
        return when (dataModel) {
            is BookingOpened -> getBookingOpenedStopAddress(dataModel)
            is IntermediateStopLocationsChanged -> getUpdatedStopAddress(dataModel)
            else -> getNoAddress()
        }
    }

    private fun getBookingOpenedStopAddress(dataModel: BookingOpened): StopAddressModel {
        val stopAddresses = dataModel.data.intermediateStopLocations
        return if (stopAddresses.isNotEmpty())
            getFirstAddress(stopAddresses)
        else
            getNoAddress()
    }

    private fun getUpdatedStopAddress(dataModel: IntermediateStopLocationsChanged): StopAddressModel {
        val stopAddresses = dataModel.data
        return if (stopAddresses.isNotEmpty())
            getFirstAddress(stopAddresses)
        else
            flagLastStop()
    }

    private fun getFirstAddress(stopAddresses: List<Location>): StopAddressModel {
        val firstStopAddress = stopAddresses[0].address
        return if (!firstStopAddress.isNullOrBlank())
            StopAddressModel(nextIntermediateStopAddress = firstStopAddress)
        else
            getNoAddress()
    }

    private fun getNoAddress() = StopAddressModel(nextIntermediateStopAddress = CLEAR)

    private fun flagLastStop() = StopAddressModel(nextIntermediateStopAddress = LAST_STOP)

}