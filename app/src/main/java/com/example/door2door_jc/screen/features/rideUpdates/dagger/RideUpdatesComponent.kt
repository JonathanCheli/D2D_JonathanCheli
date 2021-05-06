package com.example.door2door_jc.screen.features.rideUpdates.dagger

import com.example.door2door_jc.screen.dagger.ScreenComponent
import com.example.door2door_jc.screen.features.rideUpdates.view.RideUpdatesLayout
import com.example.door2door_jc.base.dagger.FeatureScope
import dagger.Component



@FeatureScope
@Component(dependencies = [ScreenComponent::class], modules = [RideUpdatesModule::class])
interface RideUpdatesComponent{

    fun injectRideUpdatesView(rideUpdatesLayout: RideUpdatesLayout)
}