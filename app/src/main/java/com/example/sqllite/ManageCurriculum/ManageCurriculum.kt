package com.example.sqllite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.sqllite.R


class ManageCurriculum : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_curriculum)
        // access the items of the list
        val g_course_array = resources.getStringArray(R.array.course_arrays)
        val g_concept_array = resources.getStringArray(R.array.concept_arrays)
        val g_sub_concept_array = resources.getStringArray(R.array.sub_concept_arrays);
        // access the spinner
        val g_spinner = findViewById<Spinner>(R.id.course_Id)
        val g_concept_spinner = findViewById<Spinner>(R.id.concept_Id);
        val g_sub_concept_spinner = findViewById<Spinner>(R.id.sub_concept_Id);
        if (g_spinner != null) {
            val l_adapter = ArrayAdapter(this,
                    android.R.layout.simple_spinner_item, g_course_array)
            g_spinner.adapter = l_adapter


            g_spinner.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    if(position==0)
                        return ;
                    Toast.makeText(this@ManageCurriculum,
                            getString(R.string.course_promt) + " " +
                                    "" + g_course_array[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
        if (g_concept_spinner != null) {
            val l_adapter = ArrayAdapter(this,
                    android.R.layout.simple_spinner_item, g_concept_array)
            g_concept_spinner.adapter = l_adapter


            g_concept_spinner.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    if(position==0)
                        return ;
                    Toast.makeText(this@ManageCurriculum, g_concept_array[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
        if (g_sub_concept_spinner != null) {
            val l_adapter = ArrayAdapter(this,
                    android.R.layout.simple_spinner_item, g_sub_concept_array)
            g_sub_concept_spinner.adapter = l_adapter


            g_sub_concept_spinner.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    if(position==0)
                        return ;
                    Toast.makeText(this@ManageCurriculum, g_sub_concept_array[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
    }

    fun  openViewContent(view : View)
    {
        val intent = Intent(this, Content::class.java)
        startActivity(intent);


    }
}

