package com.jkontodos.cabifystore.ui.store

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jkontodos.cabifystore.R
import com.jkontodos.cabifystore.databinding.FragmentStoreBinding
import com.jkontodos.cabifystore.domain.Product
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoreFragment : Fragment() {
    private lateinit var binding: FragmentStoreBinding
    private val viewModel: StoreViewModel by viewModels()
    private lateinit var productsAdapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.model.observe(viewLifecycleOwner){
            when(it){
                is StoreViewModel.UiModel.Loading -> showLoading()
                is StoreViewModel.UiModel.Failure -> showError(it.message)
                is StoreViewModel.UiModel.Failure.ServerError -> showError("Server Error")
                is StoreViewModel.UiModel.Failure.ServerErrorWithException -> showError("Server Error")
                is StoreViewModel.UiModel.Failure.UnauthorizedError -> showError("Server Error")
                is StoreViewModel.UiModel.SuccessProducts -> setProducts(it.productList)
            }
        }

        configAdapter()
        viewModel.getProducts()
    }

    private fun showLoading() {
        //TODO("Not yet implemented")
    }

    private fun showError(message: String?) {
        Log.e("Store Fragment - ", message?:"error")
    }

    private fun setProducts(productList: List<Product>) {
        productsAdapter.updateOnlineOrderList(productList)
    }

    private fun configAdapter(){
        val mockupProduct = Product(
            code = "",
            name = "",
            price = 0.0
        )
        val mockupProductList = listOf((0..10)).flatten().map { mockupProduct }

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvProducts.layoutManager = layoutManager
        productsAdapter =
            ProductsAdapter(mockupProductList, object : ProductOnClickListener {
                override fun onClickProduct(productCode: String) {
                    //TODO("Not yet implemented")
                }
            })
        binding.rvProducts.adapter = productsAdapter
    }

}