package com.example.door2door_jc.screen.features.rideUpdates.presenter

import android.util.Log
import com.example.door2door_jc.screen.features.rideUpdates.mapper.BookingStatusMapper
import com.example.door2door_jc.screen.features.rideUpdates.mapper.StopAddressMapper
import com.example.door2door_jc.screen.features.rideUpdates.view.RideUpdatesView
import com.example.door2door_jc.screen.interactor.ScreenInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RideUpdatesPresenterImp @Inject constructor(
        private val rideUpdatesView: RideUpdatesView,
        private val screenInteractor: ScreenInteractor,
        private val bookingStatusMapper: BookingStatusMapper,
        private val stopAddressMapper: StopAddressMapper
) : RideUpdatesPresenter {

    private val disposables = CompositeDisposable()
    private val tag = RideUpdatesPresenterImp::class.simpleName

    override fun viewAttached() {
        subscribeToWebSocketUpdates()
    }

    override fun viewDetached() {
        disposables.dispose()
    }

    private fun subscribeToWebSocketUpdates() {
        subscribeToBookingStatusUpdates()
        subscribeToStopLocationsUpdates()
    }

    private fun subscribeToBookingStatusUpdates() {
        disposables.add(
            screenInteractor.getBookingStatusUpdates(bookingStatusMapper)
                .subscribeOn(Schedulers.io()) //thread to run on
                .observeOn(AndroidSchedulers.mainThread()) //thread subscriber runs on
                .subscribe({
                    if (!it.dropOffAddress.isNullOrBlank() && !it.pickupAddress.isNullOrBlank())
                        rideUpdatesView.updateStartEndAddresses(it.pickupAddress, it.dropOffAddress)

                    rideUpdatesView.updateStatus(it.status, it.isBookingClosed)
                    rideUpdatesView.toggleNextStopAddressVisibility(it.status)
                }, {
                    Log.d(tag, "Error on getting status updates")
                })
        )
    }

    private fun subscribeToStopLocationsUpdates() {
        disposables.add(
            screenInteractor.getStopLocationsUpdates(stopAddressMapper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    rideUpdatesView.updateNextStopAddress(it.nextIntermediateStopAddress)
                }, {
                    Log.d(tag, "Error on getting next Stop updates")
                })
        )
    }

}