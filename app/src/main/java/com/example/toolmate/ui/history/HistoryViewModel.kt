package com.example.toolmate.ui.history

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.toolmate.data.database.History
import com.example.toolmate.data.repository.HistoryRepository

class HistoryViewModel(application: Application) : ViewModel() {
    private val mHistoryRepository: HistoryRepository = HistoryRepository(application)

    fun getAllHistory(): LiveData<List<History>> = mHistoryRepository.getAllHistory()

    fun delete(history: History) {mHistoryRepository.delete(history)}
}