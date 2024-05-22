package com.jkontodos.cabifystore.ui.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jkontodos.cabifystore.R
import com.jkontodos.cabifystore.databinding.ItemProductBinding
import com.jkontodos.cabifystore.domain.Product
import com.jkontodos.cabifystore.ui.commons.visibleOrGone

class ProductsAdapter(
    private var productList: List<Product>,
    private val listener: ProductOnClickListener
) : RecyclerView.Adapter<TransactionsViewHolder>() {

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder =
        TransactionsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_product,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        holder.bind(productList[position], listener)
    }

    fun updateOnlineOrderList(productList: List<Product>) {
        this.productList = productList
        notifyDataSetChanged()
    }
}

class TransactionsViewHolder(private val binding: ItemProductBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        product: Product,
        listener: ProductOnClickListener
    ) {
        binding.name = product.name
        binding.price = "${product.price}€"

        if (product.name.isNotEmpty() && product.price > 0.0){
            binding.cvProduct.setOnClickListener {
                listener.onClickProduct(product.code)
            }
            binding.lyProduct.visibleOrGone(true)
            binding.flShimmer.visibleOrGone(false)
        }
    }
}