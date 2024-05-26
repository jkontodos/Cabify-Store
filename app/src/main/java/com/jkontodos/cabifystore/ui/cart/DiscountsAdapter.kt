package com.jkontodos.cabifystore.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jkontodos.cabifystore.R
import com.jkontodos.cabifystore.databinding.ItemDiscountBinding
import com.jkontodos.cabifystore.domain.CartDiscounts

class DiscountsAdapter(
    private var discounts: List<CartDiscounts>
) : RecyclerView.Adapter<DiscountsViewHolder>() {

    override fun getItemCount(): Int {
        return discounts.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscountsViewHolder =
        DiscountsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_discount,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: DiscountsViewHolder, position: Int) {
        holder.bind(discounts[position])
    }
}

class DiscountsViewHolder(private val binding: ItemDiscountBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(discount: CartDiscounts) {
        binding.name = discount.name
        binding.price = "-${discount.price}€"
    }
}