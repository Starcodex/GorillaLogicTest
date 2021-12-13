package com.starcodex.gorillatest.ui.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.starcodex.gorillatest.R

class CartAdapter(var cartOperation: CartOperation, val isCart : Boolean) : ListAdapter<CartItem, CartAdapter.CartViewHolder>(DIFF_CALLBACK) {

    var onItemClickListener: (CartItem.() -> Unit)? = null
    val selectedPos = RecyclerView.NO_POSITION

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CartItem>() {
            override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem) = oldItem.name == newItem.name
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder =
        CartViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_cart_items, parent, false))

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.itemView.setSelected(selectedPos == position)
        holder.bindTo(getItem(position))
    }


    open inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val removeButton = itemView.findViewById<LinearLayout>(R.id.buttonRemove)
        private val quantity = itemView.findViewById<TextView>(R.id.quantity)
        private val name = itemView.findViewById<TextView>(R.id.productName)
        private val price = itemView.findViewById<TextView>(R.id.productPrice)
        private val type = itemView.findViewById<TextView>(R.id.type)

        fun bindTo(model: CartItem) {

            quantity.text = "x${model.quantity}"
            name.text = model.name
            price.text = "$${model.price}"
            type.text = when(model.type){
                "flavor" -> "Ice cream"
                else -> model.type
            }
            removeButton.setOnClickListener {
                cartOperation.removeItem(model)
            }

            if(!isCart){
                quantity.visibility = View.GONE
                removeButton.visibility = View.GONE
            }
        }
    }

}