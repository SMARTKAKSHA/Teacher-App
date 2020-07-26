package com.example.sqllite

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class StudentInfo : AppCompatActivity() {
    var g_student_name: TextView? = null
    var g_student_batch: TextView? = null
    var g_student_course: TextView? = null
    var g_student_dob: TextView? = null
    var g_student_add: TextView? = null
    var g_student_id: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_info)
        g_student_name = findViewById(R.id.name_textView)
        g_student_batch = findViewById(R.id.batch_textview)
        g_student_course = findViewById(R.id.course_textview)
        g_student_dob = findViewById(R.id.dob_textView)
        g_student_add = findViewById(R.id.add_textview)
        g_student_id = findViewById(R.id.studentId_textView)
        fetchDetails()
    }

//fetchDetails() function fetch details of student from server
    fun fetchDetails() {


        val SERVER_URL_STUDENT = "http://192.168.43.91/android_db/getSelectedStudentDetails.php"

        val stringRequest: StringRequest = object : StringRequest(Method.POST, SERVER_URL_STUDENT, Response.Listener { response ->
            try {

                val l_ST_ID = "ST_id"
                val l_ST_FIRSTNAME = "ST_Firstname"
                val l_ST_LASTNAME = "ST_Lastname"
                val l_ST_ADDRESS = "ST_Address"
                val l_ST_DOB = "ST_Dob"


                val jsonObject = JSONObject(response)
                val result = jsonObject.getJSONArray("result")
                var l_ST_Id: Int? = null
                var l_ST_Firstname: String? = null
                var l_ST_Lastname: String? = null
                var l_ST_Address: String? = null
                var l_ST_Dob: String? = null

                for (i in 0 until result.length()) {
                    val jo = result.getJSONObject(i)
                    l_ST_Id = jo.getInt(l_ST_ID)

                    l_ST_Firstname = jo.getString(l_ST_FIRSTNAME)
                    l_ST_Lastname = jo.getString(l_ST_LASTNAME)
                    l_ST_Address = jo.getString(l_ST_ADDRESS)
                    l_ST_Dob = jo.getString(l_ST_DOB)

                    var name: String = l_ST_Firstname + " " + l_ST_Lastname
                    g_student_name?.setText(name)
                    g_student_add?.setText(l_ST_Address)
                    g_student_dob?.setText(l_ST_Dob)
                    g_student_id?.setText(l_ST_Id.toString())


                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, Response.ErrorListener { Toast.makeText(this@StudentInfo, "Connect to Internet & try again", Toast.LENGTH_LONG).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): MutableMap<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                var l_studentID: Int = intent.getIntExtra("ST_ID", 1)
                //sending the user email and password for verification to the php file
                params["ST_ID"] = l_studentID.toString()

                return params
            }
        }
        MySingleton.getInstance(this@StudentInfo)!!.addToRequestQue(stringRequest)
    }


}