package com.example.door2door_jc.screen.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.door2door_jc.databinding.FeatureMapBinding
import com.example.door2door_jc.screen.dagger.DaggerScreenComponent
import com.example.door2door_jc.screen.dagger.ScreenComponent
import com.example.door2door_jc.screen.dagger.ScreenModule
import com.example.door2door_jc.screen.presenter.ScreenPresenter
import kotlinx.android.synthetic.main.feature_map.*
import javax.inject.Inject


class ScreenActivity : AppCompatActivity() {

    private lateinit var binding: FeatureMapBinding

    var view : View? = null

    @Inject
    lateinit var screenPresenter: ScreenPresenter

    val screenComponent: ScreenComponent by lazy {
        DaggerScreenComponent.builder()
            .screenModule(ScreenModule(this))
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
        hideActionBar()
        setContentView(com.example.door2door_jc.R.layout.activity_maps)
        binding = FeatureMapBinding.inflate(layoutInflater)
        this.binding.root
        mapView.onCreate(savedInstanceState)
        screenPresenter.viewCreated()


    }



    private fun hideActionBar() {
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
            Log.d(ScreenActivity::class.simpleName, "Error in hiding action bar")
        }
    }

    private fun injectDependencies() {
        screenComponent.injectMainScreenActivity(this)
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

}