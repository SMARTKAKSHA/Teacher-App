package com.example.sqllite

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class VIewContent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_v_iew_content)
        val l_concept_ID = intent.getStringExtra("CN_ID")
        val l_course_ID = intent.getStringExtra("CO_ID")
        val l_subconcept_ID = intent.getStringExtra("SC_ID")

        var listView = findViewById<ListView>(R.id.listview)
        var db = sqlite(this)

        var l_content: ArrayList<String>
        l_content = db.displayContent(Integer.parseInt(l_course_ID),Integer.parseInt(l_concept_ID),Integer.parseInt(l_subconcept_ID))

        var l_arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, l_content)
        listView.adapter = l_arrayAdapter

    }
}