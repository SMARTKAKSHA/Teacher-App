package com.example.sqllite

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


/*Created By Amrit Kumar
This Activity is Created for Displaying Courses
 */
class Course : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)

        var listView = findViewById<ListView>(R.id.listview)
        var db = sqlite(this)
        var l_courseArray: ArrayList<String>
        l_courseArray = db.displayCourse()
        var l_arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, l_courseArray)
        listView.adapter = l_arrayAdapter
        listView.setOnItemClickListener { adapterView, view, position: Int, id: Long ->

            var l_selectedCourse: String = l_courseArray.get(position)
            var l_course_ID: String? = db.getCourseID(l_selectedCourse)
            intent = Intent(this, ConceptActivity::class.java)
            intent.putExtra("COURSE_ID", l_course_ID)
            startActivity(intent)
        }
    }
}