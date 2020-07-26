package com.example.sqllite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast

/*Created By Amrit Kumar
This Activity is Created for Displaying Concepts
 */
class ConceptActivity : AppCompatActivity() {
    var l_CO_ID:String?=null
    lateinit var textView: TextView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_concept)
        textView = findViewById<TextView>(R.id.textView)
        openConcept();
    }


    fun openConcept() {
         l_CO_ID = intent.getStringExtra("COURSE_ID")
        var listView = findViewById<ListView>(R.id.listview)
        var db = sqlite(this)
        var l_concept_array: ArrayList<String>
        //displayConcpet() return array of concept list
        l_concept_array = db.displayConcept(Integer.parseInt(l_CO_ID!!))

        var l_arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, l_concept_array)

        listView.adapter = l_arrayAdapter

        listView.setOnItemClickListener { adapterView, view, position: Int, id: Long ->
            var l_concept_Id = db.getConceptID(l_concept_array.get(position))
            intent = Intent(this, SubConcept::class.java)
            intent.putExtra("CN_ID",l_concept_Id)
            intent.putExtra("CO_ID",l_CO_ID)
            startActivity(intent)
            Toast.makeText(this@ConceptActivity, "coid="+l_CO_ID, Toast.LENGTH_LONG).show()


        }
    }
}