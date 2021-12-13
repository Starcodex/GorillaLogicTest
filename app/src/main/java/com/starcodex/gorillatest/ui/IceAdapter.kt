package com.starcodex.gorillatest.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.starcodex.gorillatest.R
import com.starcodex.gorillatest.data.remote.IceItem
import de.hdodenhof.circleimageview.CircleImageView

class IceAdapter : ListAdapter<IceItem, IceAdapter.IceViewHolder>(DIFF_CALLBACK) {

    var onItemClickListener: (IceItem.() -> Unit)? = null
    val selectedPos = RecyclerView.NO_POSITION

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<IceItem>() {
            override fun areItemsTheSame(oldItem: IceItem, newItem: IceItem) = oldItem.name == newItem.name
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: IceItem, newItem: IceItem) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IceViewHolder =
        IceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_menu_item, parent, false))

    override fun onBindViewHolder(holder: IceViewHolder, position: Int) {
        holder.itemView.setSelected(selectedPos == position)
        holder.bindTo(getItem(position))
    }


    inner class IceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val image: CircleImageView = itemView.findViewById(R.id.ice_image)

        fun bindTo(model: IceItem) {
            Glide.with(image.getContext())
                .load(model.imageUrl)
                .into(image)
            itemView.setOnClickListener { onItemClickListener?.invoke(model) }
        }
    }


}