package be.supinfo.supermarketapp.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import be.supinfo.supermarketapp.R
import com.bumptech.glide.Glide
import java.text.NumberFormat

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    Glide.with(view.context).load(R.drawable.ic_food).into(view)
}

@BindingAdapter("price")
fun loadPrice(view: TextView, price: Double) {
    val formatter = NumberFormat.getCurrencyInstance()
    view.text = "${formatter.format(price)}"
}