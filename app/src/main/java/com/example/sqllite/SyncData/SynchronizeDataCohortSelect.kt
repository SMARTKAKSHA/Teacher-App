package com.example.sqllite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import java.util.ArrayList

class SynchronizeDataCohortSelect : AppCompatActivity() {

    var g_course_id: String?= null
    var g_sessionplan_id: String?= null
    var g_sessionplan_name: String?= null
    var g_concept_id: String?= null
    var g_content_id: String? = null
    var g_content_id1: String? = null
    var g_subconcept_id: String?= null

    var g_course_selected: String?= null
    var g_allCohorts: Spinner? = null
    var g_allCourse: Spinner? = null

    var g_cohortss: List<String> = ArrayList()        //list to be set to spinner
    var g_course: List<String> = ArrayList()           //list to be set to spinner
    var g_adapter: ArrayAdapter<String>? = null
    var db: sqlite? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_synchronize_data_cohort_select)
        g_allCohorts = findViewById<View>(com.example.sqllite.R.id.spinner_cohorts) as Spinner
        g_allCourse = findViewById<View>(com.example.sqllite.R.id.spinner_course) as Spinner
        db = sqlite(this)

        //showing cohort using spinner
        prepareCohort()
        //showing courses using spinner
        prepareCourse()

    }

    //prepare  data for spinner(cohort) setting values to spinner
    fun prepareCohort()
    {
        g_cohortss = db!!.getAllCohorts()
        //adapter for spinner
        g_adapter = ArrayAdapter(this@SynchronizeDataCohortSelect, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, g_cohortss)
        //attach adapter to spinner
        g_allCohorts!!.adapter = g_adapter

        //handle click of spinner item
        g_allCohorts!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // clicked item will be shown as spinner
                Toast.makeText(applicationContext, "" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }

    //prepare  data for spinner(course) setting values to spinner
    fun prepareCourse()
    {
        g_course = db!!.getAllCourse()
        //adapter for spinner
        g_adapter = ArrayAdapter(this@SynchronizeDataCohortSelect, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, g_course)
        //attach adapter to spinner
        g_allCourse!!.adapter = g_adapter

        //handle click of spinner item
        g_allCourse!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // clicked item will be shown as spinner
                Toast.makeText(applicationContext, "" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show()
                g_course_selected= parent.getItemAtPosition(position).toString()

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }
    fun Sync_Data(view: View?)
    {
        val intent = Intent(this@SynchronizeDataCohortSelect, SynchronizeData::class.java)
        startActivity(intent)
    }
}