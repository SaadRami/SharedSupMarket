package be.supinfo.supermarketapp.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import be.supinfo.supermarketapp.R
import be.supinfo.supermarketapp.data.remote.Product
import java.text.NumberFormat

const val ADD = "add"
const val REMOVE = "remove"

class ProductsRecyclerViewAdapter(
    val context: Context, private val products: List<Product>,
    private val itemListener: ProductListener?, val isCart: Boolean
) :
    RecyclerView.Adapter<ProductsRecyclerViewAdapter.ViewHolder>() {

    constructor(context: Context, products: List<Product>) :
            this(context, products, null, false) {
    }

    private val formatter: NumberFormat = NumberFormat.getCurrencyInstance()
    private lateinit var parentView: ConstraintLayout

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
//        val holderLayoutRes = if (MyHelper.getDisplayMode(context) == DISPLAY_GRID) {
//            R.layout.product_grid_item
//        } else {
//            R.layout.product_list_item
//        }
        val holderLayoutRes = R.layout.product_list_item
        val view = inflater.inflate(holderLayoutRes, parent, false)
        parentView = view.findViewById(R.id.product_item)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        with(holder) {
            title.text = product.title
//            description.text = product.description
            price.text = formatter.format(product.price)
            price.text = "${product.price}€"
//            price.text = "${product.price} €"
            ratingBar.rating = product.rating.toFloat()
            //  Glide.with(context).load(product.imageUrl).into(image)
        }
        if (isCart) {

            parentView.isClickable = false
            parentView.isFocusable = false

            with(holder) {
                addQuantity.visibility = View.VISIBLE
                quantityValue.visibility = View.VISIBLE
                reduceQuantity.visibility = View.VISIBLE
                addToCartBtn.visibility = View.INVISIBLE
                deleteFromCart.visibility = View.VISIBLE

                quantityValue.text = "${product.quantityInCart}"

                addQuantity.setOnClickListener {
                    var qt = "${quantityValue.text}".toInt()
                    qt++
                    quantityValue.text = "${qt}"
                    product.quantityInCart = qt
                    itemListener?.onUpdateFabCounter(1, ADD)
                }

                reduceQuantity.setOnClickListener {
                    var qt = "${quantityValue.text}".toInt()
                    if (qt > 1) {
                        qt--
                        quantityValue.text = "${qt}"
                        product.quantityInCart = qt
                        itemListener?.onUpdateFabCounter(1, REMOVE)
                    }
                }

                deleteFromCart.setOnClickListener {
                    itemListener?.onRemoveProductInCart(product)
                }
            }
        } else {
            holder.itemView.setOnClickListener { itemListener?.onProductItemClick(product) }
            holder.addToCartBtn.setOnClickListener { itemListener?.onAddToCartClick(product) }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.item_title)
        val description = itemView.findViewById<TextView>(R.id.item_desc)
        val price = itemView.findViewById<TextView>(R.id.item_price)
        val ratingBar = itemView.findViewById<RatingBar>(R.id.ratingBar)
        val image = itemView.findViewById<ImageView>(R.id.item_image)
        val addToCartBtn = itemView.findViewById<ImageButton>(R.id.btnAdd_to_cart)
        val quantityValue = itemView.findViewById<TextView>(R.id.tvQuantityValue)
        val reduceQuantity = itemView.findViewById<ImageButton>(R.id.ibReduceQuantity)
        val addQuantity = itemView.findViewById<ImageButton>(R.id.ibAddQuantity)
        val deleteFromCart = itemView.findViewById<ImageView>(R.id.ibDeleteProduct)
    }

    interface ProductListener {
        fun onProductItemClick(product: Product)
        fun onAddToCartClick(product: Product)
        fun onRemoveProductInCart(product: Product)
        fun onUpdateFabCounter(quantity: Int, updateFlag: String)
    }

}

