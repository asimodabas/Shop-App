package com.example.shopapp.ui.fragment.categories_detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopapp.R
import com.example.shopapp.common.viewBinding
import com.example.shopapp.databinding.FragmentCategoriesDetailBinding
import com.example.shopapp.ui.fragment.categories_detail.adapter.CategoriesDetailRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesDetailFragment : Fragment(R.layout.fragment_categories_detail) {

    private val binding by viewBinding(FragmentCategoriesDetailBinding::bind)
    private val viewModel by viewModels<CategoriesDetailViewModel>()
    private val args by navArgs<CategoriesDetailFragmentArgs>()
    private lateinit var categoriesDetailRecyclerAdapter: CategoriesDetailRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRv()
        observeProductsData()

        viewModel.getProductsByCategoryId(args.categoryId)
    }

    private fun setupRv() {
        binding.categoriesDetailRV.apply {
            categoriesDetailRecyclerAdapter = CategoriesDetailRecyclerAdapter(viewModel)
            adapter = categoriesDetailRecyclerAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeProductsData() {
        viewModel.productsState.observe(viewLifecycleOwner) { state ->
            state.success?.let { response ->
                categoriesDetailRecyclerAdapter.submitList(response.products.filter { it.categoryId == args.categoryId })
            }

            state.error?.let { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}