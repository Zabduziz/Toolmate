package com.example.toolmate.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [History::class],
    version = 3 // New Version include link download
)
abstract class HistoryRoomDatabase: RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: HistoryRoomDatabase? = null

        // Migration to new Database
        private val MIGRATION_1_2 = object : Migration(1,2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE history ADD COLUMN downloadlink TEXT")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2,3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE history ADD COLUMN userid TEXT")
            }
        }

        @JvmStatic
        fun getDatabase(context: Context): HistoryRoomDatabase {
            if (INSTANCE == null) {
                synchronized(HistoryRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        HistoryRoomDatabase::class.java,
                        "history_database"
                    ).addMigrations(MIGRATION_1_2,MIGRATION_2_3).build() // Add Migration
                }
            }
            return INSTANCE as HistoryRoomDatabase
        }
    }
}