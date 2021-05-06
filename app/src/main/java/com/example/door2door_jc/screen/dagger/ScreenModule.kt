package com.example.door2door_jc.screen.dagger

import android.content.res.Resources
import com.example.door2door_jc.base.dagger.AppScope
import com.example.door2door_jc.screen.interactor.ScreenInteractor
import com.example.door2door_jc.screen.interactor.ScreenInteractorImp
import com.example.door2door_jc.screen.presenter.ScreenPresenter
import com.example.door2door_jc.screen.presenter.ScreenPresenterImp
import com.example.door2door_jc.screen.view.ScreenActivity
import dagger.Module
import dagger.Provides


@Module
class ScreenModule(private val screenView: ScreenActivity) {

    @AppScope
    @Provides
    fun providesMainScreenPresenter(
        mainScreenPresenter: ScreenPresenterImp
    ): ScreenPresenter =
        mainScreenPresenter

    @AppScope
    @Provides
    fun providesMainScreenInteractor(
        mainScreenInteractor: ScreenInteractorImp
    ): ScreenInteractor =
        mainScreenInteractor

    @AppScope
    @Provides
    fun providesResources(): Resources = screenView.resources
}