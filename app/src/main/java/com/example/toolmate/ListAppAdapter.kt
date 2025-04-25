package com.example.toolmate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListAppAdapter(private val listapp: ArrayList<apps>): RecyclerView.Adapter<ListAppAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_app_row, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ListViewHolder,
        position: Int
    ) {
        val (img, name) = listapp[position]
        Glide.with(holder.imgapp.context)
            .load(img)
            .into(holder.imgapp)
        holder.tvName.text = name
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, listapp[holder.adapterPosition].name, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = listapp.size

    class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imgapp: ImageView = itemView.findViewById(R.id.img_item_appicon)
        val tvName: TextView = itemView.findViewById(R.id.tv_app)
    }
}