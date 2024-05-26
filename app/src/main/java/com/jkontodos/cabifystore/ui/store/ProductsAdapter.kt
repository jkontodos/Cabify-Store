package com.jkontodos.cabifystore.ui.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jkontodos.cabifystore.R
import com.jkontodos.cabifystore.databinding.ItemProductBinding
import com.jkontodos.cabifystore.domain.Product
import com.jkontodos.cabifystore.ui.commons.visibleOrGone
import com.jkontodos.cabifystore.ui.commons.visibleOrNot

class ProductsAdapter(
    private var productList: List<Product>,
    private val listener: ProductOnClickListener
) : RecyclerView.Adapter<ProductsViewHolder>() {

    private var expandedPosition = -1

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder =
        ProductsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_product,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(productList[position], listener, position, expandedPosition)
        holder.itemView.setOnClickListener {
            val previousExpandedPosition = expandedPosition
            expandedPosition = if (expandedPosition == position) -1 else position
            notifyItemChanged(previousExpandedPosition)
            notifyItemChanged(position)
        }
    }

    /**
     * Updates the product list in the adapter with a new one.
     *
     * @param productList The updated product list.
     */
    fun updateProductList(productList: List<Product>) {
        this.productList = productList
        notifyDataSetChanged()
    }
}

class ProductsViewHolder(private val binding: ItemProductBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        product: Product,
        listener: ProductOnClickListener,
        position: Int,
        expandedPosition: Int
    ) {
        binding.name = product.name
        binding.price = "${product.price}€"

        if (product.name.isNotEmpty() && product.price > 0.0){
            binding.lyAddToCart.visibleOrGone(position == expandedPosition)
            binding.tvExpand.visibleOrNot(position != expandedPosition)

            binding.btnAddToCard.setOnClickListener {
                listener.onAddToCartProduct(product)
            }

            when(product.code){
                "VOUCHER" -> binding.ivProductType.setImageResource(R.drawable.ic_voucher)
                "TSHIRT" -> binding.ivProductType.setImageResource(R.drawable.ic_tshirt)
                "MUG" -> binding.ivProductType.setImageResource(R.drawable.ic_mug)
            }
            binding.lyProduct.visibleOrGone(true)
            binding.flShimmer.visibleOrGone(false)
        }
    }
}