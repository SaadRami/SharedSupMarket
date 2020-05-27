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
import be.supinfo.supermarketapp.util.MyHelper
import com.bumptech.glide.Glide
import java.text.NumberFormat

class ProductsRecyclerViewAdapter(
    val context: Context, private val products: List<Product>,
    private val itemListener: ProductListener
) :
    RecyclerView.Adapter<ProductsRecyclerViewAdapter.ViewHolder>() {

    private val formatter: NumberFormat = NumberFormat.getCurrencyInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)


        val holderLayoutRes = if (MyHelper.getDisplayMode(context) == DISPLAY_GRID) {
            R.layout.product_grid_item
        } else {
            R.layout.product_list_item
        }

        val view = inflater.inflate(holderLayoutRes, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        with(holder) {
            title.text = product.title
            description.text = product.description
            price.text = formatter.format(product.price)
//            price.text = "${product.price} €"
            ratingBar.rating = product.rating.toFloat()
            Glide.with(context).load(product.imageUrl).into(image)
        }

        holder.itemView.setOnClickListener { itemListener.onItemClick(product) }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.item_title)
        val description = itemView.findViewById<TextView>(R.id.item_desc)
        val price = itemView.findViewById<TextView>(R.id.item_price)
        val ratingBar = itemView.findViewById<RatingBar>(R.id.ratingBar)
        val image = itemView.findViewById<ImageView>(R.id.item_image)
    }


    interface ProductListener {
        fun onItemClick(product: Product)
    }
}

