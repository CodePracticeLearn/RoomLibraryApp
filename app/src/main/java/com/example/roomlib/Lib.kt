package com.example.roomlib

import android.content.Context
import androidx.room.*

@Entity(tableName = "MindOrksDb")
data class Chapter(
    @PrimaryKey
    @ColumnInfo(name = "chapterName") val chapterName: String
)

@Dao
interface ChapterDao {
    @Insert
    fun insert(chapter: Chapter)
    @Query("SELECT * FROM MindOrksDb ORDER BY chapterName ASC")
    fun getAllChapter(): List<Chapter>
}

@Database(entities = arrayOf(Chapter::class), version = 1)
abstract class ChapterDatabase : RoomDatabase() {
    abstract fun chapterDao(): ChapterDao
    companion object {
        private var INSTANCE: ChapterDatabase? = null
        fun getDatabase(context: Context): ChapterDatabase? {
            if (INSTANCE == null) {
                synchronized(ChapterDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        ChapterDatabase::class.java, "chapter.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}