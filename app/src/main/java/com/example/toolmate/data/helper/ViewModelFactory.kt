package com.example.toolmate.data.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.toolmate.ui.account.AccountViewModel
import com.example.toolmate.ui.authentication.LoginViewModel
import com.example.toolmate.ui.downloader.DownloaderViewModel
import com.example.toolmate.ui.history.HistoryViewModel

class ViewModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(DownloaderViewModel::class.java)) {
            return DownloaderViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            return AccountViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}