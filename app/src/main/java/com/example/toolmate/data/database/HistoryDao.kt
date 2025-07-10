package com.example.toolmate.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history ORDER BY datedownload DESC")
    fun getAllHistory(): LiveData<List<History>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(history: History)

    @Delete
    fun delete(history: History)
}