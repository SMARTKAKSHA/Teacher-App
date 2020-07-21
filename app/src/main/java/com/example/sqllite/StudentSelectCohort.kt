package com.example.sqllite

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.ArrayList

class StudentSelectCohort : AppCompatActivity() {

    var g_cohortss: List<String> = ArrayList()        //list to be set to spinner
    var g_allCohorts: Spinner? = null
    var db: sqlite? = null
    var g_adapter: ArrayAdapter<String>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_select_cohort)
        db = sqlite(this)

        g_allCohorts = findViewById<View>(com.example.sqllite.R.id.spinner_cohorts) as Spinner

        //showing cohort using spinner
        prepareCohort()
    }
    //prepare  data for spinner(cohort) setting values to spinner
    fun prepareCohort()
    {
        g_cohortss = db!!.getAllCohorts()
        //adapter for spinner
        g_adapter = ArrayAdapter(this@StudentSelectCohort, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, g_cohortss)
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

    //onclick for Next Activity Student Performance
     fun Next(view: View?)
    {
        val intent = Intent(this@StudentSelectCohort, SearchStudent::class.java)
        startActivity(intent)
    }


    //creating option menu for logout
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(com.example.sqllite.R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val l_id = item.itemId
        if (l_id == com.example.sqllite.R.id.action_logout) {
            logout()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    //onclick for logging out from the teacher's account
    private fun logout()
    {
        val intent = Intent(this@StudentSelectCohort, MainActivity::class.java)
        startActivity(intent)
    }
}