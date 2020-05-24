package be.supinfo.supermarketapp.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.supinfo.supermarketapp.R
import be.supinfo.supermarketapp.data.Product
import be.supinfo.supermarketapp.util.JD_IMAGE
import com.bumptech.glide.Glide

class ProductsRecyclerViewAdapter(val context: Context, val products: List<Product>) :
    RecyclerView.Adapter<ProductsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.product_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        with(holder) {
            title.text = product.title
            description.text = product.description
            //price.text = product.price.toString()
            price.text = "${product.price} €"
            ratingBar.rating = product.rating.toFloat()
            Glide.with(context).load(JD_IMAGE).into(image)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.item_title)
        val description = itemView.findViewById<TextView>(R.id.item_desc)
        val price = itemView.findViewById<TextView>(R.id.item_price)
        val ratingBar = itemView.findViewById<RatingBar>(R.id.ratingBar)
        val image = itemView.findViewById<ImageView>(R.id.item_image)
    }


    
}