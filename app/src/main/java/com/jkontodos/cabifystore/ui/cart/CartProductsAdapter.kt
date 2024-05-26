package com.jkontodos.cabifystore.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jkontodos.cabifystore.R
import com.jkontodos.cabifystore.databinding.ItemCartProductBinding
import com.jkontodos.cabifystore.domain.CartProduct
import com.jkontodos.cabifystore.ui.commons.visibleOrGone

class CartProductsAdapter(
    private var cartProducts: List<CartProduct>
) : RecyclerView.Adapter<CartProductsViewHolder>() {

    override fun getItemCount(): Int {
        return cartProducts.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductsViewHolder =
        CartProductsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_cart_product,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CartProductsViewHolder, position: Int) {
        holder.bind(cartProducts[position])
    }

    /**
     * Updates the cart product list in the adapter with a new one.
     *
     * @param cartProductList The updated cart product list.
     */
    fun updateCardProductList(cartProductList: List<CartProduct>) {
        this.cartProducts = cartProductList
        notifyDataSetChanged()
    }
}

class CartProductsViewHolder(private val binding: ItemCartProductBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(product: CartProduct) {
        binding.name = product.name
        binding.price = "${product.price}€"
        binding.priceDiscount = product.priceDiscount
        binding.quantity = product.quantity.toString()

        if (product.name.isNotEmpty() && product.price > 0.0) {
            binding.ivRemove.setOnClickListener {
                // TODO: Here, the onclick for the button to remove the product from the cart would be implemented
            }

            when (product.code) {
                "VOUCHER" -> binding.ivProductType.setImageResource(R.drawable.ic_voucher)
                "TSHIRT" -> binding.ivProductType.setImageResource(R.drawable.ic_tshirt)
                "MUG" -> binding.ivProductType.setImageResource(R.drawable.ic_mug)
            }
            binding.lyCartProduct.visibleOrGone(true)
            binding.flShimmer.visibleOrGone(false)
        }
    }
}