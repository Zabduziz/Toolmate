package com.example.toolmate.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.toolmate.data.database.History
import com.example.toolmate.data.database.HistoryDao
import com.example.toolmate.data.database.HistoryRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HistoryRepository(application: Application) {
    private val mHistoryDao: HistoryDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = HistoryRoomDatabase.Companion.getDatabase(application)
        mHistoryDao = db.historyDao()
    }

    fun getAllHistory(): LiveData<List<History>> = mHistoryDao.getAllHistory()

    fun insert(history: History) {executorService.execute { mHistoryDao.insert(history) } }

    fun delete(history: History) {executorService.execute { mHistoryDao.delete(history) } }

    fun deleteAllHistory() {executorService.execute { mHistoryDao.deleteAllHistory() }}

    fun insertAll(historyList: List<History>) { mHistoryDao.insertAll(historyList) }
}