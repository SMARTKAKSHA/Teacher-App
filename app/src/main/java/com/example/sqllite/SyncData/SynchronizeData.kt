package com.example.sqllite

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

/*Created  By Divyanshu Gupta
This is the Synchronize Data activity for synchronizing data from server to local database
 */

class SynchronizeData : AppCompatActivity() {
    var mydb1: sqlite? = null
    var g_syncstatus:TextView? = null
    val g_JSON_ARRAY = "result"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mydb1 = sqlite(this)
        setContentView(R.layout.activity_synchronize_data)
        g_syncstatus = findViewById<View>(R.id.sync1) as TextView?
    }

    //onclick function for syncing the session
    fun sync_session_1(view: View?)
    {
        getCohortData()
        getCourseData()
        getContent()
        getConcept()
        getSubConcept()
        getSessionPlan()
        getSessionSection()
        getCourseContent()

        g_syncstatus?.setText("Synced")
    }

    fun sync_session_2(view: View?) {}
    fun sync_session_3(view: View?) {}

    fun getCohortData()
    {
        val url: String = SERVER_URL_COHORT
        val stringRequest = StringRequest(url, object : Response.Listener<String?> {


            override fun onResponse(response: String?) {
                response?.let { showJSON_for_cohort(it) }
            }
        },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError) {
                        Toast.makeText(this@SynchronizeData, error.message.toString(), Toast.LENGTH_LONG).show()
                    }
                })
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    private fun showJSON_for_cohort(response: String) {
        val l_CH_ID = "CH_id"
        val l_CH_NAME = "CH_Name"
        val l_TC_ID = "TC_id"
        val l_CU_ID = "CU_id"
        val l_TP_ID = "TP_id"
        val l_CH_SEMESTER = "CH_Semester"
        try
        {
            val jsonObject = JSONObject(response)
            val result = jsonObject.getJSONArray(g_JSON_ARRAY)
            var l_CH_id:Int?=null
            var l_CH_Name:String?= null
            var l_TC_id:Int?=null
            var l_CU_id:Int?=null
            var l_TP_id:Int?=null
            var l_CH_Semester:Int?= null

            for (i in 0 until result.length()) {
                val jo = result.getJSONObject(i)
                l_CH_id=jo.getInt(l_CH_ID)
                l_CH_Name=jo.getString(l_CH_NAME)
                l_TC_id=jo.getInt(l_TC_ID)
                l_CU_id=jo.getInt(l_CU_ID)
                l_TP_id=jo.getInt(l_TP_ID)
                l_CH_Semester=jo.getInt(l_CH_SEMESTER)

                mydb1?.insertData_into_Cohort(l_CH_id,l_CH_Name,l_TC_id,l_CU_id,l_TP_id,l_CH_Semester)
            }
        }
        catch (e: JSONException)
        {
            e.printStackTrace()
        }
    }


    fun getCourseData() {

        val url: String = SERVER_URL_COURSE
        val stringRequest = StringRequest(url, object : Response.Listener<String?> {


            override fun onResponse(response: String?) {
                response?.let { showJSON_for_course(it) }
            }
        },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError) {
                        Toast.makeText(this@SynchronizeData, error.message.toString(), Toast.LENGTH_LONG).show()
                    }
                })
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    private fun showJSON_for_course(response: String) {
        val l_CO_ID = "CO_id"
        val l_CO_NAME = "CO_Name"
        val l_CO_DESC = "CO_Desc"
        val l_CO_DURATION = "CO_Duration"
        val l_CO_IMAGE = "CO_Image"
        val l_CO_INSERTDATE = "CO_Insertdate"
        try {
            val jsonObject = JSONObject(response)
            val result = jsonObject.getJSONArray(g_JSON_ARRAY)

            var l_CO_id: Int? = null
            var l_CO_Name: String? = null
            var l_CO_Desc: String? = null
            var l_CO_Duration: Int? = null
            var l_CO_Image: String? = null
            var l_CO_InsertDate: String? = null


            for (i in 0 until result.length()) {
                val jo = result.getJSONObject(i)

                l_CO_id = jo.getInt(l_CO_ID)
                l_CO_Name = jo.getString(l_CO_NAME)
                l_CO_Desc = jo.getString(l_CO_DESC)
                l_CO_Duration = jo.getInt(l_CO_DURATION)
                l_CO_Image = jo.getString(l_CO_IMAGE)
                l_CO_InsertDate = jo.getString(l_CO_INSERTDATE)

                mydb1?.insertData_into_Course(l_CO_id, l_CO_Name, l_CO_Desc, l_CO_Duration, l_CO_Image, l_CO_InsertDate)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }


    fun getContent() {

        val url: String = SERVER_URL_CONTENT
        val stringRequest = StringRequest(url, object : Response.Listener<String?> {


            override fun onResponse(response: String?) {
                response?.let { showJSON1(it) }
            }
        },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError) {
                        Toast.makeText(this@SynchronizeData, error.message.toString(), Toast.LENGTH_LONG).show()
                    }
                })
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    private fun showJSON1(response: String) {
        val l_CT_ID = "CT_id"
        val l_CT_NAME= "CT_Name"
        val l_CT_TYPE = "CT_Type"
        val l_CT_CONTENTLINK= "CT_ContentLink"
        val l_CT_DURATION = "CT_Duration"
        val l_CT_INSERTDATE= "CT_InsertDate"
        val l_CT_DOWNLOADLINK="CT_DownloadLink"
        val l_CT_DOWNLOADSTATUS="CT_DownloadStatus"

        try {
            val jsonObject = JSONObject(response)
            val result = jsonObject.getJSONArray(g_JSON_ARRAY)
            var l_CT_id:Int?=null
            var l_CT_Name:String?= null
            var l_CT_Type:String?=null
            var l_CT_ContentLink:String?=null
            var l_CT_Duration:Int?=null
            var l_CT_InsertDate:String?= null
            var l_CT_DownloadLink:String?= null
            var l_CT_DownloadStatus:String?= null

            for (i in 0 until result.length()) {
                val jo = result.getJSONObject(i)
                l_CT_id=jo.getInt(l_CT_ID)
                l_CT_Name=jo.getString(l_CT_NAME)
                l_CT_Type=jo.getString(l_CT_TYPE)
                l_CT_ContentLink=jo.getString(l_CT_CONTENTLINK)
                l_CT_Duration=jo.getInt(l_CT_DURATION)
                l_CT_InsertDate=jo.getString(l_CT_INSERTDATE)
                l_CT_DownloadLink=jo.getString(l_CT_DOWNLOADLINK)
                l_CT_DownloadStatus=jo.getString(l_CT_DOWNLOADSTATUS)
                mydb1?.insertData_into_Content(l_CT_id,l_CT_Name,l_CT_Type,l_CT_ContentLink,l_CT_Duration,l_CT_InsertDate,l_CT_DownloadLink,l_CT_DownloadStatus)
                Toast.makeText(this@SynchronizeData,"Synced",Toast.LENGTH_LONG).show()
            }
        }
        catch (e: JSONException) {
            e.printStackTrace()
        }
    }


    fun getConcept() {

        val url: String = SERVER_URL_CONCEPT
        val stringRequest = StringRequest(url, object : Response.Listener<String?> {


            override fun onResponse(response: String?) {
                response?.let { showJSON(it) }
            }
        },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError) {
                        Toast.makeText(this@SynchronizeData, error.message.toString(), Toast.LENGTH_LONG).show()
                    }
                })
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    private fun showJSON(response: String)
    {
        val l_CN_ID = "CN_id"
        val l_CN_NAME= "CN_Name"
        val l_CN_DESC = "CN_Desc"
        val l_CN_DURATION = "CN_Duration"
        val l_CN_IMAGE = "CN_Image"
        val l_CN_INSERTDATE= "CN_Insertdate"
        val l_CO_CN_ID="CO_id"

        try {
            val jsonObject = JSONObject(response)
            val result = jsonObject.getJSONArray(g_JSON_ARRAY)
            var l_CN_id:Int?=null
            var l_CN_Name:String?=null
            var l_CN_Desc:String?=null
            var l_CN_Duration:Int?=null
            var l_CN_Image:String?=null
            var l_CN_Insertdate:String?=null
            var l_CO_CN_id:Int?=null

            for (i in 0 until result.length()) {
                val jo = result.getJSONObject(i)
                l_CN_id=jo.getInt(l_CN_ID)
                l_CN_Name=jo.getString(l_CN_NAME)
                l_CN_Desc=jo.getString(l_CN_DESC)
                l_CN_Duration=jo.getInt(l_CN_DURATION)
                l_CN_Image=jo.getString(l_CN_IMAGE)
                l_CN_Insertdate=jo.getString(l_CN_INSERTDATE)
                l_CO_CN_id=jo.getInt(l_CO_CN_ID)

                mydb1?.insertData_into_Concept(l_CN_id,l_CN_Name,l_CN_Desc,l_CN_Duration,l_CN_Image,l_CN_Insertdate,l_CO_CN_id)
                Toast.makeText(this@SynchronizeData,"Synced",Toast.LENGTH_LONG).show()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }


    fun getSubConcept() {

        val url: String = SERVER_URL_SUBCONCEPT
        val stringRequest = StringRequest(url, object : Response.Listener<String?> {


            override fun onResponse(response: String?) {
                response?.let { showJSON2(it) }
            }
        },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError) {
                        Toast.makeText(this@SynchronizeData, error.message.toString(), Toast.LENGTH_LONG).show()
                    }
                })
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    private fun showJSON2(response: String) {

        val l_SC_ID = "SC_id"
        val l_SC_NAME= "SC_Name"
        val l_SC_DESC = "SC_Desc"
        val l_SC_INSERTDATE= "SC_Insertdate"
        val l_SC_DURATION = "SC_Duration"
        val l_CN_SC_ID="CN_id"
        val l_CO_SC_ID="CO_id"


        try {
            val jsonObject = JSONObject(response)
            val result = jsonObject.getJSONArray(g_JSON_ARRAY)
            var l_SC_id:Int?=null
            var l_SC_Name:String?=null
            var l_SC_Desc:String?=null
            var l_SC_Insertdate:String?=null
            var l_SC_Duration:Int?=null
            var l_CN_SC_id:Int?=null
            var l_CO_SC_id:Int?=null

            for (i in 0 until result.length()) {
                val jo = result.getJSONObject(i)
                l_SC_id=jo.getInt(l_SC_ID)
                l_SC_Name=jo.getString(l_SC_NAME)
                l_SC_Desc=jo.getString(l_SC_DESC)
                l_SC_Insertdate=jo.getString(l_SC_INSERTDATE)
                l_SC_Duration=jo.getInt(l_SC_DURATION)
                l_CN_SC_id=jo.getInt(l_CN_SC_ID)

                l_CO_SC_id=jo.getInt(l_CO_SC_ID)


                mydb1?.insertData_into_SubConcept(l_SC_id,l_SC_Name,l_SC_Desc,l_SC_Insertdate,l_SC_Duration,l_CN_SC_id,l_CO_SC_id)
                Toast.makeText(this@SynchronizeData,"Synced",Toast.LENGTH_LONG).show()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    fun getSessionPlan() {

        val url: String = SERVER_URL_SESSIONPLAN
        val stringRequest = StringRequest(url, object : Response.Listener<String?> {


            override fun onResponse(response: String?) {
                response?.let { showJSON3(it) }
            }
        },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError) {
                        Toast.makeText(this@SynchronizeData, error.message.toString(), Toast.LENGTH_LONG).show()
                    }
                })
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    private fun showJSON3(response: String) {
        val l_SP_ID = "SP_id"
        val l_SP_NAME= "SP_Name"
        val l_SP_DURATION = "SP_Duration"
        val l_SP_SEQUENCE= "SP_Sequence"
        val l_CO_SP_ID = "CO_id"


        try {
            val jsonObject = JSONObject(response)
            val result = jsonObject.getJSONArray(g_JSON_ARRAY)
            var l_SP_id:Int?=null
            var l_SP_Name:String?= null
            var l_SP_Duration:Int?=null
            var l_SP_Sequence:Int?=null
            var l_CO_SP_id:Int?=null

            for (i in 0 until result.length()) {
                val jo = result.getJSONObject(i)
                l_SP_id=jo.getInt(l_SP_ID)
                l_SP_Name=jo.getString(l_SP_NAME)
                l_SP_Duration=jo.getInt(l_SP_DURATION)
                l_SP_Sequence=jo.getInt(l_SP_SEQUENCE)
                l_CO_SP_id=jo.getInt(l_CO_SP_ID)

                mydb1?.insertData_into_SessionPlan(l_SP_id,l_SP_Name,l_SP_Duration,l_SP_Sequence,l_CO_SP_id)
                Toast.makeText(this@SynchronizeData,"Synced",Toast.LENGTH_LONG).show()
            }
        }
        catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    fun getSessionSection() {

        val url: String = SERVER_URL_SESSIONSECTION
        val stringRequest = StringRequest(url, object : Response.Listener<String?> {


            override fun onResponse(response: String?) {
                response?.let { showJSON4(it) }
            }
        },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError) {
                        Toast.makeText(this@SynchronizeData, error.message.toString(), Toast.LENGTH_LONG).show()
                    }
                })
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    private fun showJSON4(response: String) {
        val l_SS_ID = "SS_id"
        val l_SS_CONTENT= "SS_Content"
        val l_SS_CONTENTTYPE = "SS_ContentType"
        val l_SS_SEQNUM= "SS_Seqnum"
        val l_SS_DURATION = "SS_Duration"
        val l_SP_SS_ID="SP_id"
        val l_SC_SS_ID="SC_id"
        val l_CO_SS_ID="CO_id"
        val l_CT_SS_ID="CT_id"

        try {
            val jsonObject = JSONObject(response)
            val result = jsonObject.getJSONArray(g_JSON_ARRAY)
            var l_SS_id:Int?=null
            var l_SS_Content:String?= null
            var l_SS_ContentType:String?=null
            var l_SS_Seqnum:Int?=null
            var l_SS_Duration:Int?=null
            var l_SP_SS_id:Int?=null
            var l_SC_SS_id:Int?=null
            var l_CO_SS_id:Int?=null
            var l_CT_SS_id:Int?=null


            for (i in 0 until result.length()) {
                val jo = result.getJSONObject(i)
                l_SS_id=jo.getInt(l_SS_ID)
                l_SS_Content=jo.getString(l_SS_CONTENT)
                l_SS_ContentType=jo.getString(l_SS_CONTENTTYPE)
                l_SS_Seqnum=jo.getInt(l_SS_SEQNUM)
                l_SS_Duration=jo.getInt(l_SS_DURATION)
                l_SP_SS_id=jo.getInt(l_SP_SS_ID)
                l_SC_SS_id=jo.getInt(l_SC_SS_ID)
                l_CO_SS_id=jo.getInt(l_CO_SS_ID)
                l_CT_SS_id=jo.getInt(l_CT_SS_ID)


                mydb1?.insertData_into_SessionSection(l_SS_id,l_SS_Content,l_SS_ContentType,l_SS_Seqnum,l_SS_Duration,l_SP_SS_id,l_SC_SS_id,l_CO_SS_id,l_CT_SS_id)
                Toast.makeText(this@SynchronizeData,"Synced",Toast.LENGTH_LONG).show()
            }
        }
        catch (e: JSONException) {
            e.printStackTrace()
        }
    }



    fun getCourseContent() {

        val url: String = SERVER_URL_COURSECONTENT
        val stringRequest = StringRequest(url, object : Response.Listener<String?> {


            override fun onResponse(response: String?) {
                response?.let { showJSON7(it) }
            }
        },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError) {
                        Toast.makeText(this@SynchronizeData, error.message.toString(), Toast.LENGTH_LONG).show()
                    }
                })
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    private fun showJSON7(response: String) {

        val l_CO_CC_ID = "CO_id"
        val l_CN_CC_ID = "CN_id"
        val l_SC_CC_ID = "SC_id"
        val l_CT_CC_ID = "CT_id"
        try {
            val jsonObject = JSONObject(response)
            val result = jsonObject.getJSONArray(g_JSON_ARRAY)
            var l_CO_id:Int?=null
            var l_CN_id:Int?=null
            var l_SC_id:Int?=null
            var l_CT_id:Int?=null


            for (i in 0 until result.length())
            {
                val jo = result.getJSONObject(i)
                l_CO_id=jo.getInt(l_CO_CC_ID)
                l_CN_id=jo.getInt(l_CN_CC_ID)
                l_SC_id=jo.getInt(l_SC_CC_ID)
                l_CT_id=jo.getInt(l_CT_CC_ID)

                mydb1?.insertData_into_CourseContent(l_CO_id,l_CN_id,l_SC_id,l_CT_id)
                Toast.makeText(this@SynchronizeData,"Synced",Toast.LENGTH_LONG).show()

            }
        }
        catch (e: JSONException)
        {
            e.printStackTrace()
        }
    }

    companion object
    {
        const val SERVER_URL_CONTENT = "http:/10.0.2.2/poc/getContent.php"
        const val SERVER_URL_CONCEPT = "http:/10.0.2.2/poc/getConcept.php"
        const val SERVER_URL_SUBCONCEPT = "http:/10.0.2.2/poc/getSubConcept.php"
        const val SERVER_URL_SESSIONPLAN = "http:/10.0.2.2/poc/getSessionPlan.php"
        const val SERVER_URL_SESSIONSECTION = "http:/10.0.2.2/poc/getSessionSection.php"
        const val SERVER_URL_COHORT = "http:/10.0.2.2/poc/getCohort.php"
        const val SERVER_URL_COURSE = "http:/10.0.2.2/poc/getCourse.php"
        const val SERVER_URL_COURSECONTENT = "http:/10.0.2.2/poc/getCourseContent.php"
    }
}