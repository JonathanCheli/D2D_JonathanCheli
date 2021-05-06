package com.example.door2door_jc.screen.dagger

import android.content.res.Resources
import com.example.door2door_jc.base.dagger.AppScope
import com.example.door2door_jc.connection.NetworkModule
import com.example.door2door_jc.screen.interactor.ScreenInteractor
import com.example.door2door_jc.screen.presenter.ScreenPresenter
import com.example.door2door_jc.screen.view.ScreenActivity
import dagger.Component


@AppScope
@Component(modules = [ScreenModule::class, NetworkModule::class])
interface ScreenComponent {

    fun injectMainScreenActivity(screenActivity: ScreenActivity)

    val screenPresenter: ScreenPresenter

    val screenInteractor: ScreenInteractor

    val resources: Resources
}