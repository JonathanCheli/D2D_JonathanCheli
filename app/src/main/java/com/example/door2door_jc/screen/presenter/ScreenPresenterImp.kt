package com.example.door2door_jc.screen.presenter

import com.example.door2door_jc.screen.interactor.ScreenInteractor
import javax.inject.Inject

class ScreenPresenterImp @Inject constructor(
    private val screenInteractor: ScreenInteractor) : ScreenPresenter {

    override fun viewCreated() {
        screenInteractor.connectToWebSocket()
    }
}