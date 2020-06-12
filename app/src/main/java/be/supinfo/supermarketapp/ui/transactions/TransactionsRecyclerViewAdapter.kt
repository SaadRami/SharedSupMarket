package be.supinfo.supermarketapp.ui.transactions

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import be.supinfo.supermarketapp.R
import be.supinfo.supermarketapp.data.local.TransactionsWithProductsAndCartProducts
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")

class TransactionsRecyclerViewAdapter(
    val context: Context, private val transactions: List<TransactionsWithProductsAndCartProducts>,
    private val itemListener: TransactionListener?
) :
    RecyclerView.Adapter<TransactionsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val holderLayoutRes = R.layout.transaction_list_item
        val view = inflater.inflate(holderLayoutRes, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = transactions.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transactionStore = transactions[position]
        with(holder) {
            //  transactionId.text = transactionStore.transaction.transactionId.toString()
            transactionDate.text =
                transactionStore.transaction.transactionDate!!.format(dateTimeFormatter)
            transactionTotal.text =
                String.format(
                    "%.2f€", transactionStore.transaction.totalTransaction
                )
            rvTransactionProdcts.adapter = TransactionProductsRecyclerViewAdapter(
                context,
                transactionStore.products,
                transactionStore.cartProducts
            )
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        val transactionId = itemView.findViewById<TextView>(R.id.tvTransactionId)
        val transactionTotal = itemView.findViewById<TextView>(R.id.tvTransactionTotal)
        val rvTransactionProdcts = itemView.findViewById<RecyclerView>(R.id.rvTransactionProducts)
        val transactionDate = itemView.findViewById<TextView>(R.id.tvTransactionId)
    }

    interface TransactionListener {
    }

}

