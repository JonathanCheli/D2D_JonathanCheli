package com.example.door2door_jc.screen.features.mapFeature.presenter

import android.util.Log
import com.example.door2door_jc.screen.features.mapFeature.mapper.StatusMapper
import com.example.door2door_jc.screen.features.mapFeature.mapper.StopLocationsMapper
import com.example.door2door_jc.screen.features.mapFeature.mapper.VehicleLocationMapper
import com.example.door2door_jc.screen.features.mapFeature.model.BOOKING_CLOSED
import com.example.door2door_jc.screen.features.mapFeature.view.MapView
import com.example.door2door_jc.screen.interactor.ScreenInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MapPresenterImp @Inject constructor(
        private val mapView: MapView,
        private val screenInteractor: ScreenInteractor,
        private val vehicleLocationMapper: VehicleLocationMapper,
        private val stopLocationsMapper: StopLocationsMapper,
        private val statusMapper: StatusMapper
) : MapPresenter {

    private val disposables = CompositeDisposable()
    private val tag = MapPresenterImp::class.simpleName



    override fun viewAttached() {
        mapView.obtainGoogleMap()
    }

    override fun viewDetached() {
        disposables.dispose()
    }

    override fun mapLoaded() {
        subscribeToWebSocketUpdates()
    }

    private fun subscribeToWebSocketUpdates() {
        this.subscribeToVehicleLocationUpdates()
        this.subscribeToStopLocationsUpdates()
        this.subscribeToStatusUpdates()
    }

    private fun subscribeToVehicleLocationUpdates() {
        disposables.add(screenInteractor.getVehicleLocationUpdates(vehicleLocationMapper)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mapView.updateVehicleMarker(it.latLng)
            }, {
                Log.d(tag, "Error on getting vehicle location updates")
            })
        )
    }

    private fun subscribeToStopLocationsUpdates() {
        disposables.add(screenInteractor.getStopLocationsUpdates(stopLocationsMapper)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.pickupLatLng != null && it.dropOffLatLng != null) {
                    mapView.showStartEndMarkers(
                        it.pickupLatLng,
                        it.dropOffLatLng,it.intermediateStopLatLng
                    )
                }
                mapView.updateStopsMarkers(it.intermediateStopLatLng)
            }, {
                Log.d(tag, "Error on getting vehicle location updates")
            })
        )
    }

    private fun subscribeToStatusUpdates() {
        disposables.add(
            screenInteractor.getBookingStatusUpdates(statusMapper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.status == BOOKING_CLOSED)
                        mapView.clearAllMarkers()
                }, {
                    Log.d(tag, "Error on getting vehicle location updates")
                })
        )
    }
}