package com.example.toolmate.ui.downloader

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.toolmate.data.database.History
import com.example.toolmate.data.repository.HistoryRepository

class DownloaderViewModel(application: Application): ViewModel() {
    private val mHistoryRepository: HistoryRepository = HistoryRepository(application)

    fun insert(history: History) {mHistoryRepository.insert(history)}
}