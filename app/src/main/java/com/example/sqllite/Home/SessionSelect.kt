package com.example.sqllite

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
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
    var g_session_id: String?= null
    var g_curriculum_id: String?= null
    private val PREFS_NAME2 = "SYNC_STATUS"
    var g_syncstatus: TextView? = null
    var g_syncstatus2: TextView? = null
    var g_syncstatus3: TextView? = null

    var g_concept_id: String?= null
    var g_subconcept_id: String?= null
    var g_mydb1: sqlite? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_select)
        g_mydb1 = sqlite(this)
        g_syncstatus = findViewById<View>(R.id.sync1) as TextView?
        g_syncstatus2 = findViewById<View>(R.id.sync2) as TextView?
        g_syncstatus3 = findViewById<View>(R.id.sync3) as TextView?

        val intent = intent
        g_course_id = intent.getStringExtra("co_id")
        g_concept_id = intent.getStringExtra("cn_id")
        g_subconcept_id = intent.getStringExtra("sc_id")
        g_session_name= intent.getStringExtra("sp_name")
        g_session_id= intent.getStringExtra("sp_id")
        g_curriculum_id = intent.getStringExtra("cu_id")

        checkSyncstatus()
    }
    private fun checkSyncstatus() {
        var sp = getSharedPreferences(PREFS_NAME2, Context.MODE_PRIVATE)




        var check:Boolean = sp.getBoolean("sync1",false)
        var check2:Boolean = sp.getBoolean("sync2",false)
        var check3:Boolean = sp.getBoolean("sync3",false)

        if(check==true){
            g_syncstatus!!.setText("Synced")
        }
        else if(check==false){
            g_syncstatus!!.setText("Not Synced")
        }

        if(check2==true){
            g_syncstatus2!!.setText("Synced")
        }
        else if(check2==false){
            g_syncstatus2!!.setText("Not Synced")
        }
        if(check3==true){
            g_syncstatus3!!.setText("Synced")
        }
        else if(check3==false){
            g_syncstatus3!!.setText("Not Synced")
        }


    }
    //onclick function for starting session1 and it will open the classroom connection activity that is server class
    fun start_session1(view: View?)
    {
        val intent = Intent(this@SessionSelect, Server::class.java)
        intent.putExtra("co_id", g_course_id)
        intent.putExtra("sc_id", g_subconcept_id)
        intent.putExtra("cn_id", g_concept_id)
        intent.putExtra("sp_name", g_session_name)
        intent.putExtra("sp_id", g_session_id)
        intent.putExtra("cu_id",g_curriculum_id)

        startActivity(intent)
    }

    //onclick function for starting session 2
    fun start_session2(view: View?)
    {
        val intent = Intent(this@SessionSelect, Server::class.java)
        intent.putExtra("co_id", g_course_id)
        intent.putExtra("sc_id", g_subconcept_id)
        intent.putExtra("cn_id", g_concept_id)
        intent.putExtra("sp_name", g_session_name)
        intent.putExtra("sp_id", g_session_id)
        intent.putExtra("cu_id",g_curriculum_id)
        startActivity(intent)
    }

    //onclick function  starting session 3
    fun start_session3(view: View?)
    {
        val intent = Intent(this@SessionSelect, Server::class.java)
        intent.putExtra("co_id", g_course_id)
        intent.putExtra("sc_id", g_subconcept_id)
        intent.putExtra("cn_id", g_concept_id)
        intent.putExtra("sp_name", g_session_name)
        intent.putExtra("sp_id", g_session_id)
        intent.putExtra("cu_id",g_curriculum_id)
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
        (this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).clearApplicationUserData()// for clearing app data
        val intent = Intent(this@SessionSelect, MainActivity::class.java)
        startActivity(intent)
    }

}