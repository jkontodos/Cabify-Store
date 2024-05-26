package com.jkontodos.cabifystore.ui.commons

import android.graphics.Paint
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.jkontodos.cabifystore.R

/**
 * With this extension function, we can toggle a view between visible and gone
 *
 * @param visible The visibility to set the view to.
 */
@BindingAdapter("visibleOrGone")
fun View.visibleOrGone(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

/**
 * With this extension function, we can toggle a view between visible and invisible
 *
 * @param visible The visibility to set the view to.
 */
@BindingAdapter("visibleOrNot")
fun View.visibleOrNot(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

/**
 * With this extension function, we add the number of products in the shopping cart, and if there are more than 9, a "+9" will be fixed to the text.
 *
 * @param counter The number of products in the shopping cart.
 */
@BindingAdapter("setBagCounterText")
fun TextView.setBagCounterText(counter: Int) {
    this.text = if (counter <= 9) counter.toString() else "+9"
}

/**
 * With this extension function, we set the title of the shopping cart using the cart counter.
 *
 * @param counter The number of products in the shopping cart.
 */
@BindingAdapter("setCartTitleText")
fun TextView.setCartTitleText(counter: Int) {
    val title =
        if (counter > 0) "${context.getString(R.string.cart_title)} ($counter)" else context.getString(
            R.string.cart_title
        )
    this.text = title
}

/**
 * Extension function that strikes through a TextView based on a boolean
 *
 * @param hasDiscount A boolean that determines whether the text should be struck through or not.
 */
@BindingAdapter("priceWithDiscount")
fun TextView.priceWithDiscount(hasDiscount: Boolean) {
    if (hasDiscount) {
        this.paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    } else {
        this.paintFlags = this.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
}

/**
 * With this extension function, we set the discounted price of a product.
 *
 * @param priceDiscount The discounted price of the product.
 */
@BindingAdapter("setDiscountedPrice")
fun TextView.setDiscountedPrice(priceDiscount: Double) {
    val price = "$priceDiscount€"
    this.text = price
}