package com.example.sqllite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class WebContent : AppCompatActivity() {
    var WEBSITE: ArrayList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_content)
        var listView = findViewById<ListView>(R.id.listview)
        var db = sqlite(this)

        var intent = intent
        WEBSITE= intent.getStringArrayListExtra("web")
        var l_arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, WEBSITE!!)
        listView.adapter = l_arrayAdapter

        listView.setOnItemClickListener { adapterView, view, position: Int, id: Long ->
            var l_content_link = WEBSITE!!.get(position)
            var l_ct_id: String? = db.getContentId(WEBSITE!!.get(position))
            intent = Intent(this, WebView::class.java)

            intent.putExtra("CT_ID", l_ct_id)

            startActivity(intent)
        }
    }
}