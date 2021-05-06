package com.example.door2door_jc.screen.features.rideUpdates.dagger

import com.example.door2door_jc.screen.features.rideUpdates.presenter.RideUpdatesPresenter
import com.example.door2door_jc.screen.features.rideUpdates.presenter.RideUpdatesPresenterImp
import com.example.door2door_jc.screen.features.rideUpdates.view.RideUpdatesView
import com.example.door2door_jc.base.dagger.FeatureScope
import dagger.Module
import dagger.Provides

@Module
class RideUpdatesModule(private val rideUpdatesView: RideUpdatesView) {

    @FeatureScope
    @Provides
    fun providesRideUpdatesView() = rideUpdatesView

    @FeatureScope
    @Provides
    fun providesRideUpdatesPresenter(
        rideUpdatesPresenter: RideUpdatesPresenterImp): RideUpdatesPresenter = rideUpdatesPresenter
}