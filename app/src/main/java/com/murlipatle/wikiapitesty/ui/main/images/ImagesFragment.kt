package com.murlipatle.wikiapitesty.ui.main.images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.GridLayoutManager
import com.murlipatle.wikiapitesty.databinding.ImagesFragmentBinding
import com.murlipatle.wikiapitesty.ui.adapter.ImagePagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ImagesFragment : Fragment() {

    companion object {
        fun newInstance() = ImagesFragment()
    }
    private val viewModel by viewModels<ImagesViewModel>()

    private val categoryPagingAdapter = ImagePagingAdapter()
    private var _binding: ImagesFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ImagesFragmentBinding.inflate(inflater, container, false)
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
        binding.categoryRecycler.layoutManager = GridLayoutManager(context, 2)
        binding.categoryRecycler.adapter = categoryPagingAdapter
    }
}