package com.example.sqllite




import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import org.json.JSONException
import org.json.JSONObject
import android.content.Intent

/*
Created by Amrit and Divyanshu
 */

class SearchStudent : AppCompatActivity() {

    lateinit var g_adapter: ArrayAdapter<*>
    private lateinit var listView: ListView
    val g_JSON_ARRAY = "result"
    var g_ST_Id:Int ? = null
      var g_student_name :ArrayList<String> = arrayListOf()

    var g_name_id: MutableMap<String, Int> = mutableMapOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_student)
         title = "SearchStudent"


         listView = findViewById(R.id.listView)

        getStudentData()
        g_adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, g_student_name)
        listView.adapter = g_adapter
        listView.onItemClickListener = OnItemClickListener { adapterView, _, i, _ ->
            Toast.makeText(this@SearchStudent, adapterView.getItemAtPosition(i).toString(),
                    Toast.LENGTH_SHORT).show()
            intent = Intent(this, StudentInfo::class.java)
            intent.putExtra("ST_ID",g_name_id.getValue(adapterView.getItemAtPosition(i).toString()))
            startActivity(intent)
        }

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val search = menu.findItem(R.id.appSearchBar)
        val searchView = search.actionView as SearchView
        searchView.setBackgroundColor(Color.BLACK)
        searchView.queryHint = "Search"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                g_adapter.filter.filter(newText)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }


    fun getStudentData()
    {
        val url: String = SERVER_URL_STUDENT
        val stringRequest = StringRequest(url, object : Response.Listener<String?> {


            override fun onResponse(response: String?) {
                response?.let { showJSON_for_student(it) }
            }
        },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError) {
                        Toast.makeText(this@SearchStudent, error.message.toString(), Toast.LENGTH_LONG).show()

                    }
                })
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    private fun showJSON_for_student(response: String) {
        val l_ST_ID = "ST_id"
        val l_ST_FIRSTNAME = "ST_Firstname"
        val l_ST_LASTNAME = "ST_Lastname"

        try
        {


            val jsonObject = JSONObject(response)
            val result = jsonObject.getJSONArray(g_JSON_ARRAY)

            var l_ST_Firstname:String? = null
            var l_ST_Lastname:String?=null
            var l_ST_Id:Int?=null
            for (i in 0 until result.length()) {
                val jo = result.getJSONObject(i)
                l_ST_Id= jo.getInt(l_ST_ID)
                l_ST_Firstname= jo.getString(l_ST_FIRSTNAME)
                l_ST_Lastname= jo.getString(l_ST_LASTNAME)

                var name : String = l_ST_Firstname + " " + l_ST_Lastname
                g_student_name.add(name)
                g_name_id.put(name,l_ST_Id)

            }
        }
        catch (e: JSONException)
        {
            e.printStackTrace()
        }



    }





    companion object {

        const val SERVER_URL_STUDENT = "http://192.168.29.71/poc/getStudentDetails.php"





    }




























}