package com.example.sqllite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.sqllite.R
import com.example.sqllite.WebView
import com.example.sqllite.sqlite

class WebContent : AppCompatActivity() {
    var g_WEBSITE: ArrayList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_content)
        var listView = findViewById<ListView>(R.id.listview)
        var db = sqlite(this)

        var intent = intent
        g_WEBSITE= intent.getStringArrayListExtra("web")
        var l_arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, g_WEBSITE!!)
        listView.adapter = l_arrayAdapter

        listView.setOnItemClickListener { adapterView, view, position: Int, id: Long ->
            var l_content_link = g_WEBSITE!!.get(position)
            intent = Intent(this, WebView::class.java)

            intent.putExtra("CT_LINK", l_content_link)

            startActivity(intent)
        }
    }
}