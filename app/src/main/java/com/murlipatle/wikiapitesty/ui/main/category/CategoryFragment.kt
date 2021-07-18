package com.murlipatle.wikiapitesty.ui.main.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import com.murlipatle.wikiapitesty.databinding.CategoryFragmentBinding
import com.murlipatle.wikiapitesty.ui.adapter.CategoryPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    companion object {
        fun newInstance() = CategoryFragment()
    }

    private val viewModel by viewModels<CategoryViewModel>()

    private val categoryPagingAdapter = CategoryPagingAdapter()
    private var _binding: CategoryFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = CategoryFragmentBinding.inflate(inflater, container, false)
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
                categoryPagingAdapter.submitData(it)
            }
        }


        binding.categoryRecycler.adapter = categoryPagingAdapter
    }


}