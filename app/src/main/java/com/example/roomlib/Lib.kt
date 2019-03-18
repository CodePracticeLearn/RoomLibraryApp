package com.example.roomlib

import android.content.Context
import androidx.room.*

@Entity(tableName = "DemoTable")
data class DataClass1(
    @PrimaryKey
    @ColumnInfo(name = "demoCol1") val colName: String
)

@Dao
interface ColumnDao {
    @Insert
    fun insert(dataClass1: DataClass1)
    @Query("SELECT * FROM DemoTable ORDER BY demoCol1 ASC")
    fun getAllChapter(): List<DataClass1>
}

@Database(entities = arrayOf(DataClass1::class), version = 1)
abstract class DemoDatabase : RoomDatabase() {
    abstract fun chapterDao(): ColumnDao
    companion object {
        private var INSTANCE: DemoDatabase? = null
        fun getDatabase(context: Context): DemoDatabase? {
            if (INSTANCE == null) {
                synchronized(DemoDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        DemoDatabase::class.java, "demo.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}