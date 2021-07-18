package com.murlipatle.wikiapitesty.ui.adapter

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Scroller
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.murlipatle.wikiapitesty.databinding.ListItemArticleBinding
import com.murlipatle.wikiapitesty.room.ArticleDetail

class ArticlePagingAdapter :
    PagingDataAdapter<ArticleDetail, ArticlePagingAdapter.MyViewHolder>(DIFF_UTIL) {

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<ArticleDetail>() {
            override fun areItemsTheSame(oldItem: ArticleDetail, newItem: ArticleDetail): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ArticleDetail, newItem: ArticleDetail): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }


    inner class MyViewHolder(val viewDataBinding: ListItemArticleBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = getItem(position)

        if (item != null) {
            holder.viewDataBinding.textViewListItem.text = item.title
            holder.viewDataBinding.textViewListDescription.text = item.description
        }

     /*   Glide.with(holder.viewDataBinding.root).load(item.urlToImage)
            .into(holder.viewDataBinding.imageListItem)*/


        holder.viewDataBinding.listItemRoot.setOnClickListener {

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ListItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
}