package com.guowei.coordinatorlayoutexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SimpleAdapter : RecyclerView.Adapter<SimpleAdapter.SimpleHolder>() {

    private val ITEMS = arrayOf(
        "Alpha", "Beta", "Cupcake", "Donut",
        "Eclair", "FroYo", "Gingerbread", "Honeycomb",
        "Ice Cream Sandwich", "Jelly Bean", "KitKat",
        "Lollipop", "Marshmallow", "Nobody Knows"
    )

    class SimpleHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textView: TextView = view.findViewById(R.id.text)

        init {
            view.isClickable = true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return SimpleHolder(view)
    }

    override fun onBindViewHolder(holder: SimpleHolder, position: Int) {
        holder.textView.text = ITEMS[position]
    }

    override fun getItemCount(): Int {
        return ITEMS.size
    }
}