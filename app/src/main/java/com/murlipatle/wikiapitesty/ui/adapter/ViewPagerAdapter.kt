package com.murlipatle.wikiapitesty.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.murlipatle.wikiapitesty.ui.main.article.ArticleFragment
import com.murlipatle.wikiapitesty.ui.main.category.CategoryFragment
import com.murlipatle.wikiapitesty.ui.main.images.ImagesFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            ArticleFragment.newInstance()
        } else if (position == 1) {
            ImagesFragment.newInstance()
        } else {
            CategoryFragment.newInstance()
        }
    }

    override fun getItemCount(): Int {
        return CARD_ITEM_SIZE
    }

    companion object {
        private const val CARD_ITEM_SIZE = 3
    }
}