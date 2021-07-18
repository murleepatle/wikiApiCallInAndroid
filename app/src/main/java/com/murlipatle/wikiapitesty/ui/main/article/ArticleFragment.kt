package com.murlipatle.wikiapitesty.ui.main.article

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import com.murlipatle.wikiapitesty.databinding.ArticleFragmentBinding
import com.murlipatle.wikiapitesty.databinding.CategoryFragmentBinding
import com.murlipatle.wikiapitesty.ui.adapter.ArticlePagingAdapter
import com.murlipatle.wikiapitesty.ui.adapter.CategoryPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleFragment : Fragment() {
    private val viewModel by viewModels<ArticleViewModel>()

    private val articlePagingAdapter = ArticlePagingAdapter()
    private var _binding: ArticleFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = ArticleFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = ArticleFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pager.collectLatest {
                articlePagingAdapter.submitData(it)
            }
        }


        binding.articleRecycler.adapter = articlePagingAdapter
    }


}