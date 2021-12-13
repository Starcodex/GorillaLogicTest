package com.starcodex.gorillatest.ui.cart

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.starcodex.gorillatest.R
import com.starcodex.gorillatest.commons.DaggerViewModelFactory
import com.starcodex.gorillatest.ui.bridge.AFCommunicator
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_cart.*
import javax.inject.Inject

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DividerItemDecoration
import com.starcodex.gorillatest.ui.receipt.ReceiptFragment


class CartFragment : Fragment(), CartOperation {

    @Inject
    lateinit var factory: DaggerViewModelFactory
    lateinit var activityCommunicator : AFCommunicator
    lateinit var viewModel : CartViewModel

    val adapter : CartAdapter = CartAdapter(this, true)

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        activityCommunicator = activity as AFCommunicator
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_cart, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this, factory).get(CartViewModel::class.java)

        initRecycler()

        viewModel.cartItems.observe(this, Observer {
            adapter.submitList(emptyList())
            adapter.submitList(it)
        })

        viewModel.totalPrice.observe(this, Observer {
            totalPrice.text = "$${it}"
        })

        addItems.setOnClickListener {
            activityCommunicator.navigateTo(0)
        }

        buttonNext.setOnClickListener {
            //launch receipt fragment modal
            ReceiptFragment.newInstance().show(requireActivity().supportFragmentManager, "DialogReceipt")
        }

    }

    fun initRecycler(){
        recycler.adapter = adapter
        val mLayoutManager = LinearLayoutManager(requireContext())
        recycler.layoutManager = mLayoutManager
        val dividerItemDecoration = DividerItemDecoration(requireContext(), mLayoutManager.getOrientation())
        recycler.addItemDecoration(dividerItemDecoration)
    }

    override fun removeItem(item: CartItem) {
        if(adapter.currentList.size==1 && item.quantity==1){
            viewModel.removeItem(item)
            activityCommunicator.navigateTo(0)
        }else{
            viewModel.removeItem(item)
        }
    }
}