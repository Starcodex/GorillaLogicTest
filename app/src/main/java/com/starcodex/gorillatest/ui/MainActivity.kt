package com.starcodex.gorillatest.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.starcodex.gorillatest.R
import com.starcodex.gorillatest.commons.DaggerViewModelFactory
import com.starcodex.gorillatest.data.remote.ConfigItem
import com.starcodex.gorillatest.data.remote.IceItem
import com.starcodex.gorillatest.ui.bridge.AFCommunicator
import com.starcodex.gorillatest.ui.cart.CartItem
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), AFCommunicator {

    private lateinit var stepPagesAdapter : ScreenSlidePagerAdapter

    @Inject
    lateinit var factory: DaggerViewModelFactory
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // View Model Init
        mainViewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
        initViewPager()
    }

    fun initViewPager(){
        // View Pager Init
        stepPagesAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = stepPagesAdapter
        viewPager.isUserInputEnabled = false
    }


    override fun onDialogDismiss() {
        mainViewModel.resetCart()
        navigateTo(0)
    }


    // Navigation
    override fun navigateTo(position : Int){
        viewPager.setCurrentItem(position, false)
    }

    override fun nextPage() {
        viewPager.setCurrentItem(viewPager.currentItem+1, true)
    }

    override fun previouspage() {
        viewPager.setCurrentItem(viewPager.currentItem-1, true)
    }

    override fun showProgress(){
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress(){
        progress.visibility = View.GONE
    }

}