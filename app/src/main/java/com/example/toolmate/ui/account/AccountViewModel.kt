package com.example.toolmate.ui.account

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.toolmate.data.repository.HistoryRepository

class AccountViewModel(application: Application) : ViewModel() {
    private val mHistoryRepository: HistoryRepository = HistoryRepository(application)

    fun deleteAllHistory() {mHistoryRepository.deleteAllHistory()}
}