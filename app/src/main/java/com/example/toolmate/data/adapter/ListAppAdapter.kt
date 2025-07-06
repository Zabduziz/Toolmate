package com.example.toolmate.data.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toolmate.R
import com.example.toolmate.ui.downloader.DownloaderActivity


class ListAppAdapter(private val listapp: ArrayList<apps>): RecyclerView.Adapter<ListAppAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_app_row, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (img, name) = listapp[position]
        Glide.with(holder.imgapp.context)
            .load(img)
            .into(holder.imgapp)
        holder.tvName.text = name
        holder.itemView.setOnClickListener {
            // Kirim data ke SecondActivity saat item diklik
            val downloaderActicity = Intent(holder.itemView.context, DownloaderActivity::class.java)
            downloaderActicity.putExtra(DownloaderActivity.NAME_PLATFORM, name)
            downloaderActicity.putExtra(DownloaderActivity.LOGO_PLATFORM, img)
            holder.itemView.context.startActivity(downloaderActicity)
        }
    }


    override fun getItemCount(): Int = listapp.size

    class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imgapp: ImageView = itemView.findViewById(R.id.img_item_appicon)
        val tvName: TextView = itemView.findViewById(R.id.tv_app)
    }
}