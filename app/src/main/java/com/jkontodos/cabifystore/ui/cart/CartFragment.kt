package com.jkontodos.cabifystore.ui.cart

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jkontodos.cabifystore.R
import com.jkontodos.cabifystore.databinding.FragmentCartBinding
import com.jkontodos.cabifystore.domain.CartProduct
import com.jkontodos.cabifystore.domain.CustomerCart
import com.jkontodos.cabifystore.ui.commons.visibleOrGone
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private val viewModel: CartViewModel by viewModels()
    private lateinit var cartProductsAdapter: CartProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.model.observe(viewLifecycleOwner){
            when(it){
                is CartViewModel.UiModel.Loading -> showLoading()
                is CartViewModel.UiModel.Failure -> showError(it.message)
                is CartViewModel.UiModel.Failure.ServerError -> showError("Server Error")
                is CartViewModel.UiModel.Failure.ServerErrorWithException -> showError("Server Error")
                is CartViewModel.UiModel.Failure.UnauthorizedError -> showError("Server Error")
                is CartViewModel.UiModel.SuccessCustomerCart -> setCustomerCart(it.customerCart)
            }
        }

        binding.viewModel = viewModel
        viewModel.getCartCount()

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnProcess.setOnClickListener {
            binding.lyComplete.visibleOrGone(true)
            binding.lottieComplete.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator) {
                    viewModel.resetCustomerCart()
                }

                override fun onAnimationEnd(p0: Animator) {
                    findNavController().navigateUp()
                }

                override fun onAnimationCancel(p0: Animator) {
                }

                override fun onAnimationRepeat(p0: Animator) {
                }
            })
            binding.lottieComplete.playAnimation()
        }

        configAdapter()
        viewModel.getCustomerCart()
    }

    /** * Shows the loading indicator. */
    private fun showLoading() {
        Log.d("Store Fragment - ", "Loading")
    }

    /** * Shows the error message. */
    private fun showError(message: String?) {
        Log.e("Store Fragment - ", message?:"error")
    }

    /** * Initializes the adapter configuration for the cart product list and sets it for the RecyclerView. */
    private fun configAdapter(){
        val mockupProduct = CartProduct(
            code = "",
            name = "",
            price = 0.0,
            priceDiscount = 0.0,
            quantity = 0
        )
        val mockupProductList = listOf((0..10)).flatten().map { mockupProduct }

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvCartProducts.layoutManager = layoutManager
        cartProductsAdapter =
            CartProductsAdapter(mockupProductList)
        binding.rvCartProducts.adapter = cartProductsAdapter
    }

    /**
     * Sets the customer cart data to the UI.
     *
     * @param customerCart The customer cart data.
     */
    private fun setCustomerCart(customerCart: CustomerCart?) {
        customerCart?.let {
            setSummary(customerCart)

            cartProductsAdapter.updateCardProductList(customerCart.products)
        }?: run {
            binding.tvEmptyCart.visibleOrGone(true)
            binding.rvCartProducts.visibleOrGone(false)
            binding.lySummary.visibleOrGone(false)
        }
    }

    /**
     * Sets the summary data to the UI.
     *
     * @param customerCart The customer cart data.
     */
    private fun setSummary(customerCart: CustomerCart) {
        val subTotal = "${customerCart.subTotal}€"
        val total = "${customerCart.total}€"
        binding.tvSubTotal.text = subTotal
        binding.tvTotalPrice.text = total

        if (customerCart.discounts.isNotEmpty()){
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding.rvDiscounts.layoutManager = layoutManager
            binding.rvDiscounts.adapter = DiscountsAdapter(customerCart.discounts)
            binding.lyDiscounts.visibleOrGone(true)
        }
    }
}