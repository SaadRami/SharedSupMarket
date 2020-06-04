package be.supinfo.supermarketapp.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.supinfo.supermarketapp.R
import be.supinfo.supermarketapp.data.remote.Rayon

class RayonsRecyclerViewAdapter(
    val context: Context, private val rayons: List<Rayon>,
    private val itemListener: RayonListener
) :
    RecyclerView.Adapter<RayonsRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val holderLayoutRes = R.layout.rayon_grid_item
        val view = inflater.inflate(holderLayoutRes, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = rayons.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rayon = rayons[position]
        with(holder) {
            title.text = rayon.title
        }
        holder.itemView.setOnClickListener { itemListener.onRayonItemClick(rayon) }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.tvRayon_title)
    }

    interface RayonListener {
        fun onRayonItemClick(rayon: Rayon)
    }
}
