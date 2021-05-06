package com.example.door2door_jc.screen.features.mapFeature.dagger

import com.example.door2door_jc.screen.dagger.ScreenComponent
import com.example.door2door_jc.screen.features.mapFeature.view.MapLayout
import com.example.door2door_jc.base.dagger.FeatureScope
import dagger.Component


@FeatureScope
@Component(dependencies = [ScreenComponent::class], modules = [MapModule::class])
interface MapComponent{

    fun injectMapView(mapLayout: MapLayout)
}