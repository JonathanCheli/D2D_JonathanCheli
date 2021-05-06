package com.example.door2door_jc.screen.features.mapFeature.dagger


import com.example.door2door_jc.screen.features.mapFeature.presenter.MapPresenter
import com.example.door2door_jc.screen.features.mapFeature.presenter.MapPresenterImp
import com.example.door2door_jc.screen.features.mapFeature.view.MapView
import com.example.door2door_jc.base.dagger.FeatureScope
import dagger.Module
import dagger.Provides


@Module
class MapModule(private val mapView: MapView) {

    @FeatureScope
    @Provides
    fun providesMapView(): MapView = mapView

    @FeatureScope
    @Provides
    fun providesMapPresenter(mapPresenter: MapPresenterImp): MapPresenter = mapPresenter
}