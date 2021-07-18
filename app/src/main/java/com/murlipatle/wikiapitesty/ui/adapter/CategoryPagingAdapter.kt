package com.murlipatle.wikiapitesty.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.murlipatle.wikiapitesty.databinding.ListItemCategoryBinding
import com.murlipatle.wikiapitesty.retrofit.responce.Allcategory

class CategoryPagingAdapter :
    PagingDataAdapter<Allcategory, CategoryPagingAdapter.MyViewHolder>(DIFF_UTIL) {

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Allcategory>() {
            override fun areItemsTheSame(oldItem: Allcategory, newItem: Allcategory): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Allcategory, newItem: Allcategory): Boolean {
                return oldItem.category == newItem.category
            }
        }
    }


    inner class MyViewHolder(val viewDataBinding: ListItemCategoryBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = getItem(position)

        holder.viewDataBinding.textViewListItem.text = item!!.category

     /*   Glide.with(holder.viewDataBinding.root).load(item.urlToImage)
            .into(holder.viewDataBinding.imageListItem)*/


        holder.viewDataBinding.listItemRoot.setOnClickListener {

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ListItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
}