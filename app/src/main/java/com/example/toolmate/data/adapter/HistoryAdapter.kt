package com.example.toolmate.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toolmate.R
import com.example.toolmate.data.database.History
import com.example.toolmate.data.helper.HistoryDiffCallback
import com.example.toolmate.databinding.HistoryRowBinding

class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.ListViewHolder>() {
    private val listHistory = ArrayList<History>()
    fun setListHistory(listHistory: List<History>) {
        val diffCallback = HistoryDiffCallback(this.listHistory, listHistory)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listHistory.clear()
        this.listHistory.addAll(listHistory)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = HistoryRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listHistory[position])
    }

    override fun getItemCount(): Int {
        return listHistory.size
    }

    inner class ListViewHolder(private val binding: HistoryRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(history: History) {
            with(binding) {
                binding.tvNameMedia.text = history.medianame
                binding.tvDateDownload.text = history.datedownload
                Glide.with(binding.imgThumbnail.context)
                    .load(history.thumbnails)
                    .into(binding.imgThumbnail)
            }
        }
    }
}