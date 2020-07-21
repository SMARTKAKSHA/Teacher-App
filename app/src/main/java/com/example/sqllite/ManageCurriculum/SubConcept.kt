package com.example.sqllite

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

/*Created By Amrit Kumar
This Activity is Created for Displaying Sub concepts
 */

class SubConcept : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_concept)
        val l_content_ID = intent.getStringExtra("CN_ID")
        var listView = findViewById<ListView>(R.id.listview)
        var db = sqlite(this)
        var l_subConceptArray: ArrayList<String>
        //displaySubConcpet() return array of subconcepts list
        l_subConceptArray = db.displaySubConcept(Integer.parseInt(l_content_ID))

        var l_arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, l_subConceptArray)
        listView.adapter = l_arrayAdapter
        listView.setOnItemClickListener { adapterView, view, position: Int, id: Long ->
            var l_selectedConcept: String = l_subConceptArray.get(position)
        }
    }
}