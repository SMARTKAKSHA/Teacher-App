package com.example.sqllite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

/*
Created by  Divyanshu Gupta
Displaying pdf content
 */
class PdfContent : AppCompatActivity() {
    var g_PDF: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_content)
        var listView = findViewById<ListView>(R.id.listview)
        var db = sqlite(this)

        var intent = intent
       g_PDF= intent.getStringArrayListExtra("pdf")
        var l_arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, g_PDF!!)
        listView.adapter = l_arrayAdapter

        listView.setOnItemClickListener { adapterView, view, position: Int, id: Long ->
            var l_content_link = g_PDF!!.get(position)
         //   var l_ct_id: String? = db.getContentId(PDF!!.get(position))
            intent = Intent(this, PdfViewer::class.java)

            intent.putExtra("CT_LINK", l_content_link)

            startActivity(intent)
        }
    }
}