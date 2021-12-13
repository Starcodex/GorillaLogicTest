package com.starcodex.gorillatest.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.starcodex.gorillatest.ui.cart.CartFragment
import com.starcodex.gorillatest.util.BaseIceFragment
import com.starcodex.gorillatest.util.IceFragmentsModel

class ScreenSlidePagerAdapter(fm: AppCompatActivity) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int {
        return StepPagesModel.values().size
    }


    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> BaseIceFragment.newInstance(IceFragmentsModel.FLAVORS)
            1 -> BaseIceFragment.newInstance(IceFragmentsModel.TOPPINGS)
            2 -> CartFragment()
            else -> BaseIceFragment.newInstance(IceFragmentsModel.FLAVORS)
        }
    }

    fun getTitle(position: Int): Int {
        return StepPagesModel.values()[position].titleResId
    }
}