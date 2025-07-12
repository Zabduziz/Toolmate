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
    private var onItemClickCallback: OnItemClickCallback? = null
    private var onItemLongClickCallback: OnItemLongClickCallback? = null
    private val selectedItems = mutableSetOf<History>()

    fun getSelectedItem(): List<History> = selectedItems.toList()

    fun toggleSelection(history: History) {
        if (selectedItems.contains(history)) {
            selectedItems.remove(history)
        } else {
            selectedItems.add(history)
        }
        notifyDataSetChanged()
    }

    fun clearSelection() {
        selectedItems.clear()
        notifyDataSetChanged()
    }

    fun isItemSelected(history: History): Boolean {
        return selectedItems.contains(history)
    }

    fun setListHistory(listHistory: List<History>) {
        val diffCallback = HistoryDiffCallback(this.listHistory, listHistory)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listHistory.clear()
        this.listHistory.addAll(listHistory)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(history: History)
    }

    interface OnItemLongClickCallback {
        fun onItemLongClicked(history: History)
    }

    fun setOnItemLongClickCallback(callback: OnItemLongClickCallback) {
        onItemLongClickCallback = callback
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

                // Set Click listener di Itemview
                root.isSelected = isItemSelected(history)
                root.setBackgroundResource(
                    if (isItemSelected(history)) R.color.selected_item else android.R.color.transparent
                )

                root.setOnLongClickListener {
                    onItemLongClickCallback?.onItemLongClicked(history)
                    true
                }

                root.setOnClickListener {
                    if (selectedItems.isNotEmpty()) {
                        onItemLongClickCallback?.onItemLongClicked(history)
                    } else {
                        onItemClickCallback?.onItemClicked(history)
                    }
                }
            }
        }
    }
}