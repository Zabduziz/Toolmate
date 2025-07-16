package com.example.toolmate.ui.authentication

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toolmate.data.database.History
import com.example.toolmate.data.repository.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(application: Application): ViewModel() {
    private val mHistoryRepository: HistoryRepository = HistoryRepository(application)

    fun insertAllHistory(historyList: List<History>) {
        viewModelScope.launch(Dispatchers.IO) {
            mHistoryRepository.insertAll(historyList)
        }
    }

    fun deleteAllHistory() {
        viewModelScope.launch(Dispatchers.IO) { mHistoryRepository.deleteAllHistory() }
    }
}