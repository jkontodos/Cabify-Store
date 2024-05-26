package com.jkontodos.cabifystore.ui.commons

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
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
 * With this extension function, we set the source of an ImageView using Glide when it comes from an external URL.
 *
 * @param url The URL of the image to load.
 */
@BindingAdapter("loadImageUrl")
fun ImageView.loadImageUrl(url: String) {
    if (url.isNotEmpty() && url.isNotBlank()) {
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.logo_moradul)
            .error(R.drawable.logo_moradul)
            .into(this)
    }
}

/**
 * With this extension function, we set the source of an ImageView using Glide when it comes from a resource within the app.
 *
 * @param imgResource The resource ID of the image to load.
 */
@BindingAdapter("loadImageResource")
fun ImageView.loadImageResource(imgResource: Int) {
    Glide.with(this)
        .load(imgResource)
        .placeholder(R.drawable.logo_moradul)
        .error(R.drawable.logo_moradul)
        .into(this)
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