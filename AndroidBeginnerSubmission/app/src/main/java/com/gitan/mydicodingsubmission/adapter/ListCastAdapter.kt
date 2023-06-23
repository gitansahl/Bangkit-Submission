package com.gitan.mydicodingsubmission.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.gitan.mydicodingsubmission.data.Cast
import com.gitan.mydicodingsubmission.databinding.ItemRowCastBinding

class ListCastAdapter(private val listCast: ArrayList<Cast>): Adapter<ListCastAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    class ListViewHolder(var binding: ItemRowCastBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    fun setOnItemCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun getItemCount(): Int = listCast.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val cast = listCast[position]

        Glide.with(holder.itemView.context)
            .load(cast.photo)
            .into(holder.binding.imgItemPhoto)
        holder.binding.tvRealName.text = cast.realName
        holder.binding.tvCastDescription.text = cast.description
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listCast[holder.adapterPosition]) }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Cast)
    }
}