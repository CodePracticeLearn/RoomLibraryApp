package com.example.roomlib

import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var chapterDatabase: ChapterDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        chapterDatabase = ChapterDatabase.getDatabase(this)!!
        btnSave.setOnClickListener {
            if (!etEnterText.text.toString().isEmpty()) {
                val chapterObj = Chapter(etEnterText.text.toString())
                InsertTask(this, chapterObj).execute()
            }
        }
        btnDisplay.setOnClickListener {
            GetDataFromDb(this).execute()
        }
    }
    private class InsertTask(var context: MainActivity, var chapter: Chapter) : AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            context.chapterDatabase!!.chapterDao().insert(chapter)
            return true
        }
        override fun onPostExecute(bool: Boolean?) {
            if (bool!!) {
                Toast.makeText(context, "Added to Database", Toast.LENGTH_LONG).show()
            }
        }
    }
    private class GetDataFromDb(var context: MainActivity) : AsyncTask<Void, Void, List<Chapter>>() {
        override fun doInBackground(vararg params: Void?): List<Chapter> {
            return context.chapterDatabase!!.chapterDao().getAllChapter()
        }
        override fun onPostExecute(chapterList: List<Chapter>?) {
            if (chapterList!!.size > 0) {
                for (i in 0..chapterList.size - 1) {
                    context.tvDatafromDb.append(chapterList[i].chapterName)
                }
            }
        }
    }
}
