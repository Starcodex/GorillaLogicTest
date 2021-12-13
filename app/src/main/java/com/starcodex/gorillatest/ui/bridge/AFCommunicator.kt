package com.starcodex.gorillatest.ui.bridge

import android.view.View
import com.starcodex.gorillatest.data.remote.ConfigItem
import com.starcodex.gorillatest.data.remote.IceItem
import com.starcodex.gorillatest.ui.cart.CartItem
import kotlinx.android.synthetic.main.activity_main.*

interface AFCommunicator {
    fun nextPage()
    fun previouspage()
    fun navigateTo(position : Int)
    fun onDialogDismiss()
    fun showProgress()
    fun hideProgress()
}