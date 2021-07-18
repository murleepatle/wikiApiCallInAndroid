package com.murlipatle.wikiapitesty.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.murlipatle.wikiapitesty.R
import com.murlipatle.wikiapitesty.databinding.ListItemArticleBinding
import com.murlipatle.wikiapitesty.databinding.ListItemImageBinding
import com.murlipatle.wikiapitesty.room.ImageDetail

class ImagePagingAdapter :
    PagingDataAdapter<ImageDetail, ImagePagingAdapter.MyViewHolder>(DIFF_UTIL) {

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<ImageDetail>() {
            override fun areItemsTheSame(oldItem: ImageDetail, newItem: ImageDetail): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ImageDetail, newItem: ImageDetail): Boolean {
                return oldItem.pageId == newItem.pageId
            }
        }
    }


    inner class MyViewHolder(val viewDataBinding: ListItemImageBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = getItem(position)

        if (item != null) {

        Glide.with(holder.viewDataBinding.root).load(item.images)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.viewDataBinding.imageListItem)
        }


        holder.viewDataBinding.listItemRoot.setOnClickListener {

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ListItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
}