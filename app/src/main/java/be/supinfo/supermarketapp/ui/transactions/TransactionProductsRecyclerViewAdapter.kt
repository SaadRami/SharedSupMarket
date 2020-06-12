package be.supinfo.supermarketapp.ui.transactions


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.supinfo.supermarketapp.R
import be.supinfo.supermarketapp.data.local.CartProduct
import be.supinfo.supermarketapp.data.remote.Product

class TransactionProductsRecyclerViewAdapter(
    val context: Context,
    private val products: List<Product>, private val cartProducts: List<CartProduct>
) :
    RecyclerView.Adapter<TransactionProductsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val holderLayoutRes = R.layout.transaction_product_list_item
        val view = inflater.inflate(holderLayoutRes, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        val cartProduct = cartProducts[position]

        with(holder) {
            title.text = product.title
            price.text = String.format("%.2f€", product.price)
            quantity.text = cartProduct.quantity.toString()

        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.tvTransactionProduct_title)
        val price = itemView.findViewById<TextView>(R.id.tvTransactionProduct_price)
        val quantity = itemView.findViewById<TextView>(R.id.tvTransactionProduct_quantity)

    }

    interface TransactionProductListener {
    }

}

