package com.example.sqllite

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.sqllite.R
import org.json.JSONException
import org.json.JSONObject

/*Created  By Divyanshu Gupta
This is the TeacherHome  activity it is loaded just after the Login
In this teacher can perform four fucntion that are
1) Performing Classroom session
2) Syncing data directly from cloud to local database
3) Mangaing curricullum
4) Viewing Student Performance
 */

class TeacherHome : AppCompatActivity() {
    var g_mydb1: sqlite? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_home)
        g_mydb1 = sqlite(this)


    }

    //onclick function for starting classroom session1 and it will open the home  activity where user will enter the cohort and course
    fun classroom_session(view: View?)
    {
        val intent = Intent(this@TeacherHome, Home::class.java)
        startActivity(intent)
    }

    //onclick function for syncing data from  cloud to local database
    fun sync_data(view: View?)
    {
        val intent = Intent(this@TeacherHome, SynchronizeDataCohortSelect::class.java)
        startActivity(intent)
    }

    //onclick function for viewing and managing curicullm
    fun manage_curriculum(view: View?)
    {
        val intent = Intent(this@TeacherHome, Course::class.java)
        startActivity(intent)
    }

    //onclick function for viewing student performance
    fun student_performance(view: View?)
    {
        val intent = Intent(this, StudentSelectCohort::class.java)
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
        val intent = Intent(this@TeacherHome, MainActivity::class.java)
        startActivity(intent)
    }


}