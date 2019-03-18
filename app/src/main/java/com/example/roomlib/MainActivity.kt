package com.example.roomlib

import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var demoDatabase: DemoDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        demoDatabase = DemoDatabase.getDatabase(this)!!
        btnSave.setOnClickListener {
            if (!etEnterText.text.toString().isEmpty()) {
                val colObj = DataClass1(etEnterText.text.toString())
                InsertTask(this, colObj).execute()
            }
        }

    }
    private class InsertTask(var context: MainActivity, var dataClass1: DataClass1) : AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            context.demoDatabase!!.chapterDao().insert(dataClass1)
            return true
        }
        override fun onPostExecute(bool: Boolean?) {
            if (bool!!) {
                Toast.makeText(context, "Added to Database", Toast.LENGTH_LONG).show()
            }
        }
    }
    private class GetData(var context: MainActivity) : AsyncTask<Void, Void, List<DataClass1>>() {
        override fun doInBackground(vararg params: Void?): List<DataClass1> {
            return context.demoDatabase!!.chapterDao().getAllChapter()
        }
        override fun onPostExecute(dataClass1List: List<DataClass1>?) {
            if (dataClass1List!!.size > 0) {
                for (i in 0..dataClass1List.size - 1) {
                    context.tvDatafromDb.append(dataClass1List[i].colName)
                }
            }
        }
    }
}
