package com.example.sqllite

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import org.json.JSONException
import org.json.JSONObject
import java.util.*


/*Created  By Divyanshu Gupta
This is the main activity and hence the login activity of the app too
 */
class MainActivity : AppCompatActivity() {
    var g_email: EditText? = null
    var g_password: EditText? = null
    var remember:CheckBox?=null
    private val PREFS_NAME = "PrefsFile"
    private var preferences:SharedPreferences?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        g_email = findViewById(R.id.email)
        g_password = findViewById(R.id.pass)
         remember= findViewById<CheckBox>(R.id.remeberme)

        preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
getPreferencesData()

    }

//setting the rememberd username and password
    private fun getPreferencesData() {
        var sp = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        if(sp.contains("pref_username")) {
            var username = sp.getString("pref_username", "not found.")
            g_email!!.setText(username)
        }
        if(sp.contains("pref_password")) {
            var passsword = sp.getString("pref_password", "not found.")
            g_password!!.setText(passsword)
        }
        if(sp.contains("pref_check")) {
            var check:Boolean = sp.getBoolean("pref_check",false)
            remember!!.setChecked(check)
            val intent = Intent(this@MainActivity, TeacherHome::class.java)
            startActivity(intent)

        }


    }


    //onclick function for logging in the user and verifing the user details
    fun login_main(view: View?)
    {
        val l_email_user = g_email!!.text.toString()
        val l_password_user = g_password!!.text.toString()
        if (checkNetworkConnection())//checking whether app is connected to internet or not(if connected it will return true and hence the code below will run)
        {
            val stringRequest: StringRequest = object : StringRequest(Method.POST, g_SERVER_URL, Response.Listener { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val Response = jsonObject.getString("response")
                    if (Response == "Login Success")//the php code will return the response that is login success if user and password are correct in server database
                    {

                        //checking whether remember checkox is checked or not
                        if(remember!!.isChecked){
                            val boolIsChecked = remember!!.isChecked
                            val editor = preferences!!.edit()
                            editor.putString("pref_username",l_email_user)
                            editor.putString("pref_password",l_password_user)
                            editor.putBoolean("pref_check",boolIsChecked)
                            editor.apply()
                            Toast.makeText(this@MainActivity,"Setting have been saved",Toast.LENGTH_LONG).show()

                        }
                        else{
                            preferences!!.edit().clear().apply()
                        }

                        Toast.makeText(this@MainActivity, "Logging In", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@MainActivity, TeacherHome::class.java)
                        startActivity(intent)

                        //clearing the text boxes after directing to the other activity
                        g_email!!.text.clear()
                        g_password!!.text.clear()
                    }
                    else
                    {
                        Toast.makeText(this@MainActivity, "Wrong Email id/Password", Toast.LENGTH_LONG).show()
                    }
                }
                catch (e: JSONException)
                {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { Toast.makeText(this@MainActivity, "Connect to Internet & try again", Toast.LENGTH_LONG).show() }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    //sending the user email and password for verification to the php file
                    params["email"] = l_email_user
                    params["password"] = l_password_user
                    return params
                }
            }
            MySingleton.getInstance(this@MainActivity)!!.addToRequestQue(stringRequest)
        }

    }


    //onclick function for resetting the password this function takes you to the reset password activity
    fun forget(view: View?)
    {
        if (checkNetworkConnection()) {
            val intent = Intent(this@MainActivity, ResetPasswordActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this@MainActivity, "Connect to Internet & try again", Toast.LENGTH_LONG).show()
        }
    }

    //functon for checking whether the app is connected to internet or not
    fun checkNetworkConnection(): Boolean
    {
        val l_connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val l_networkInfo = l_connectivityManager.activeNetworkInfo
        return l_networkInfo != null && l_networkInfo.isConnected
    }



    companion object {
        const val g_SERVER_URL = "http:/10.0.2.2/poc/login.php"
    }




}


