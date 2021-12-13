package com.starcodex.gorillatest.ui.receipt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.starcodex.gorillatest.R
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_dialog_receipt.*
import kotlinx.android.synthetic.main.fragment_dialog_receipt.view.*
import android.content.DialogInterface

import android.app.Activity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.starcodex.gorillatest.commons.DaggerViewModelFactory
import com.starcodex.gorillatest.ui.bridge.AFCommunicator
import com.starcodex.gorillatest.ui.cart.CartAdapter
import com.starcodex.gorillatest.ui.cart.CartItem
import com.starcodex.gorillatest.ui.cart.CartOperation
import com.starcodex.gorillatest.ui.cart.CartViewModel
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_dialog_receipt.recycler
import javax.inject.Inject


class ReceiptFragment : DialogFragment(), CartOperation {

    companion object {

        fun newInstance(args : Bundle? = null): ReceiptFragment {
            val frag = ReceiptFragment()
            frag.setArguments(args)
            return frag
        }

    }

    @Inject
    lateinit var factory: DaggerViewModelFactory
    lateinit var activityCommunicator : AFCommunicator
    val adapter : CartAdapter = CartAdapter(this, false)
    lateinit var viewModel : CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        activityCommunicator = activity as AFCommunicator
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog_receipt, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupClickListeners(view)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupView(view: View) {
        viewModel = ViewModelProviders.of(this, factory).get(CartViewModel::class.java)
        initRecycler()
        viewModel.cartItems.observe(this, Observer {
            adapter.submitList(emptyList())
            adapter.submitList(it)
        })

        viewModel.totalPrice.observe(this, Observer {
                view.totalPrice.text  = "$${it}"
        })
    }

    fun initRecycler(){
        recycler.adapter = adapter
        val mLayoutManager = LinearLayoutManager(requireContext())
        recycler.layoutManager = mLayoutManager
        val dividerItemDecoration = DividerItemDecoration(requireContext(), mLayoutManager.getOrientation())
        recycler.addItemDecoration(dividerItemDecoration)
    }

    private fun setupClickListeners(view: View) {
        closeBtn.setOnClickListener {
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        activityCommunicator.onDialogDismiss()
    }

    override fun removeItem(item: CartItem) {

    }

}