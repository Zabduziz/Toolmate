package com.example.toolmate.data.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.toolmate.data.database.History

class HistoryDiffCallback(
    private val oldHistoryList: List<History>,
    private val newHistoryList: List<History>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int { return oldHistoryList.size }

    override fun getNewListSize(): Int { return newHistoryList.size }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldHistoryList[oldItemPosition].id == newHistoryList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldHistory = oldHistoryList[oldItemPosition]
        val newHistory = newHistoryList[newItemPosition]
        return oldHistory.medianame == newHistory.medianame
                && oldHistory.datedownload == newHistory.datedownload
                && oldHistory.thumbnails == newHistory.thumbnails
    }

}