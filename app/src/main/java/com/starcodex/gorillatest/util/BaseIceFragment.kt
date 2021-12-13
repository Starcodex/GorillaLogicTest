package com.starcodex.gorillatest.util

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.leochuan.CenterSnapHelper
import com.leochuan.CircleLayoutManager
import com.leochuan.ScrollHelper
import com.starcodex.gorillatest.R
import com.starcodex.gorillatest.commons.DaggerViewModelFactory
import com.starcodex.gorillatest.data.remote.ConfigItem
import com.starcodex.gorillatest.ui.IceAdapter
import com.starcodex.gorillatest.ui.bridge.AFCommunicator
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_ice.*
import javax.inject.Inject

open class BaseIceFragment() : Fragment() {

    lateinit var activityCommunicator : AFCommunicator
    var type : String = ""


    @Inject
    lateinit var factory: DaggerViewModelFactory
    lateinit var viewModel: BaseViewModel
    var currentPos = 5

    lateinit var adapter: IceAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_ice, container, false)


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        activityCommunicator = activity as AFCommunicator
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory,).get(BaseViewModel::class.java)

        startLoading()

        viewModel.configFileLiveData.observe(this,{
            setDataView(it)
        })
        viewModel.cartItemLiveData.observe(this,{
            Log.d("INSERTION",""+it)
            activityCommunicator.nextPage()
        })
        val disabler: RecyclerView.OnItemTouchListener = RecyclerViewDisabler()
        adapter = IceAdapter()
        recycler.adapter = adapter

        val layoutManager =
            CircleLayoutManager.Builder(requireContext())
                .setRadius(900)
                .setAngleInterval(30)
                .setDistanceToBottom(50)
                .setGravity(CircleLayoutManager.TOP)
                .build()
        layoutManager.infinite = true
        recycler.layoutManager = layoutManager
        recycler.addOnItemTouchListener(disabler)


        CenterSnapHelper().attachToRecyclerView(recycler)

        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                setItemToPosition(layoutManager.currentPosition)
            }
        })

        buttonRight.setOnClickListener {
            val currentPos = layoutManager.currentPosition
            val next = if(currentPos +1 >= adapter.itemCount ) 0 else currentPos +1
            ScrollHelper.smoothScrollToTargetView(recycler, layoutManager.findViewByPosition(next))
        }

        buttonLeft.setOnClickListener {
            val currentPos = layoutManager.currentPosition
            val next = if(currentPos -1 < 0 ) adapter.itemCount-1 else currentPos -1
            ScrollHelper.smoothScrollToTargetView(recycler, layoutManager.findViewByPosition(next))
        }

        viewModel.flavorLiveData.observe(this,{
            adapter.submitList(it)
            ScrollHelper.smoothScrollToTargetView(recycler, layoutManager.findViewByPosition(1))
            setItemToPosition(layoutManager.currentPosition)
            stopLoading()
        })

        buttonNext.setOnClickListener {
            viewModel.insertCartItem(adapter.currentList.get(currentPos), type)
        }

        initView()
    }

    fun initView(){
        arguments?.let {
            it.getString("title")?.let {
                step.text = it
                it
            }
            it.getString("subtitle")?.let {
                stepInstruction.text = it
                it
            }
            it.getString("background").let {
                parentLayout.setBackgroundColor(Color.parseColor(it))
                it
            }
            it.getString("type")?.let {
                type = it
            }
        }
        viewModel.performInit(type)
    }

    fun startLoading(){
        activityCommunicator.showProgress()
        buttonNext.isEnabled = false
        buttonRight.isClickable = false
        buttonLeft.isClickable = false
    }

    fun stopLoading(){
        activityCommunicator.hideProgress()
        buttonNext.isEnabled = true
        buttonRight.isClickable = true
        buttonLeft.isClickable = true
    }

    fun setDataView(configItem: ConfigItem){
        welcomeText.setTextColor(Color.parseColor("#${configItem.colors?.main}"))
        welcomeText.text = "Welcome To "
        storeName.setTextColor(Color.parseColor("#${configItem.colors?.secondary}"))
        storeName.text = configItem.storeName
    }


    fun setItemToPosition(pos : Int){
        if(currentPos==pos){ return }
        currentPos = pos
        val item = adapter.currentList.get(currentPos)
        productName.text = item.name
        productPrice.text = "$${item.price}"
    }

    override fun onDetach() {
        super.onDetach()
        Runtime.getRuntime().gc()
    }

    companion object {
        fun newInstance(params : IceFragmentsModel): BaseIceFragment {
            val fragment = BaseIceFragment()
            val args = Bundle()
            args.putString("title", params.title)
            args.putString("subtitle", params.subtitle)
            args.putString("background", params.backgroundColorResource)
            args.putString("type", params.type)
            fragment.setArguments(args)
            return fragment
        }
    }
}