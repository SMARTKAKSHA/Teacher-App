package com.example.sqllite

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

/*Created  By Divyanshu Gupta
This is the session select activity in which user will select the session he want's to teach
 */

class SessionSelect : AppCompatActivity() {
    var  g_course_id: String?= null
    var g_session_name: String?= null
    var g_concept_id: String?= null
    var g_subconcept_id: String?= null
    var g_mydb1: sqlite? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_select)
        g_mydb1 = sqlite(this)

        val intent = intent
        g_course_id = intent.getStringExtra("co_id")
        g_concept_id = intent.getStringExtra("cn_id")
        g_subconcept_id = intent.getStringExtra("sc_id")
        g_session_name= intent.getStringExtra("sp_name")

    }

    //onclick function for starting session1 and it will open the classroom connection activity that is server class
    fun start_session1(view: View?)
    {
        val intent = Intent(this@SessionSelect, Server::class.java)
        intent.putExtra("co_id", g_course_id)
        intent.putExtra("sc_id", g_subconcept_id)
        intent.putExtra("cn_id", g_concept_id)
        intent.putExtra("sp_name", g_session_name)

        startActivity(intent)
    }

    //onclick function for starting session 2
    fun start_session2(view: View?)
    {
        val intent = Intent(this@SessionSelect, Server::class.java)
        startActivity(intent)
    }

    //onclick function  starting session 3
    fun start_session3(view: View?)
    {
        val intent = Intent(this@SessionSelect, Server::class.java)
        startActivity(intent)
    }

    //onclick  function for starting test activity
    fun start_test(view: View?)
    {
        val intent = Intent(this@SessionSelect, Test::class.java)
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
        val intent = Intent(this@SessionSelect, MainActivity::class.java)
        startActivity(intent)
    }

}