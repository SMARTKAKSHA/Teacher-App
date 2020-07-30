package com.example.sqllite

import android.content.Intent
import android.os.Bundle
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
This is the Synchronize Data activity for synchronizing data from server to local database
 */

class SynchronizeData : AppCompatActivity() {
    var mydb1: sqlite? = null
    var g_syncstatus:TextView? = null
    val g_JSON_ARRAY = "result"
    var g_course_id: String?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mydb1 = sqlite(this)
        setContentView(R.layout.activity_synchronize_data)
        g_syncstatus = findViewById<View>(R.id.sync1) as TextView?

        val intent = intent
        g_course_id = intent.getStringExtra("co_id")

    }
    //onclick function for syncing the session
    fun sync_session_1(view: View?)
    {
        getCurriculum()
        getContent()
        getConcept()
        getSubConcept()
        getSessionPlan()
        getSessionSection()
        getCourseContent()
        getCurriculumDetails()
        g_syncstatus?.setText("Synced")
    }

    fun sync_session_2(view: View?) {}
    fun sync_session_3(view: View?) {}

    fun getCurriculum() {

        val url: String = SERVER_URL_CURRICULUM
        val stringRequest = StringRequest(url, object : Response.Listener<String?> {


            override fun onResponse(response: String?) {
                response?.let { showJSON_for_curriculum(it) }
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

    private fun showJSON_for_curriculum(response: String) {
        val l_CU_ID = "CU_id"
        val l_CU_NAME = "CU_Name"
        val l_CU_DESC = "CU_Desc"
        val l_CU_IMAGE = "CU_Image"
        val l_CU_INSERTDATE = "CU_Insertdate"
        try {
            val jsonObject = JSONObject(response)
            val result = jsonObject.getJSONArray(g_JSON_ARRAY)

            var l_CU_id: Int? = null
            var l_CU_Name: String? = null
            var l_CU_Desc: String? = null
            var l_CU_Image: String? = null
            var l_CU_InsertDate: String? = null


            for (i in 0 until result.length()) {
                val jo = result.getJSONObject(i)

                l_CU_id = jo.getInt(l_CU_ID)
                l_CU_Name = jo.getString(l_CU_NAME)
                l_CU_Desc = jo.getString(l_CU_DESC)
                l_CU_Image = jo.getString(l_CU_IMAGE)
                l_CU_InsertDate = jo.getString(l_CU_INSERTDATE)

                mydb1?.insertData_into_Curriculum(l_CU_id, l_CU_Name, l_CU_Desc, l_CU_Image, l_CU_InsertDate)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    fun getContent() {

        val url: String = SERVER_URL_CONTENT
        val stringRequest = StringRequest(url,object : Response.Listener<String?> {


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

            }
        }
        catch (e: JSONException)
        {
            e.printStackTrace()
        }
    }

    fun getCurriculumDetails() {

        val url: String = SERVER_URL_CURRICULUMDETAILS
        val stringRequest = StringRequest(url, object : Response.Listener<String?> {


            override fun onResponse(response: String?) {
                response?.let { showJSON8(it) }
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

    private fun showJSON8(response: String) {

        val l_CD_CU_ID = "CU_id"
        val l_CD_CO_ID = "CO_id"
        val l_CD_CO_SEQNO = "CO_SeqNo"
        val l_CD_CO_SEMESTER = "CO_Semester"
        val l_CD_CO_YEAR = "CO_Year"

        try {
            val jsonObject = JSONObject(response)
            val result = jsonObject.getJSONArray(g_JSON_ARRAY)
            var l_CU_id:Int?=null
            var l_CO_id:Int?=null
            var l_CO_SeqNo:Int?=null
            var l_CO_Semester:Int?=null
            var l_CO_Year:Int?=null


            for (i in 0 until result.length())
            {
                val jo = result.getJSONObject(i)
                l_CU_id=jo.getInt(l_CD_CU_ID)
                l_CO_id=jo.getInt(l_CD_CO_ID)
                l_CO_SeqNo=jo.getInt(l_CD_CO_SEQNO)
                l_CO_Semester=jo.getInt(l_CD_CO_SEMESTER)
                l_CO_Year=jo.getInt(l_CD_CO_YEAR)

                mydb1?.insertData_into_CurriculumDetails(l_CU_id,l_CO_id,l_CO_SeqNo,l_CO_Semester,l_CO_Year)

            }
        }
        catch (e: JSONException)
        {
            e.printStackTrace()
        }
    }

    companion object
    {
        const val SERVER_URL_CONTENT = "http:/192.168.29.71/poc/getContent.php"
        const val SERVER_URL_CONCEPT = "http:/192.168.29.71/poc/getConcept.php"
        const val SERVER_URL_SUBCONCEPT = "http:/192.168.29.71/poc/getSubConcept.php"
        const val SERVER_URL_SESSIONPLAN = "http:/192.168.29.71/poc/getSessionPlan.php"
        const val SERVER_URL_SESSIONSECTION = "http:/192.168.29.71/poc/getSessionSection.php"

        const val SERVER_URL_COURSECONTENT = "http:/192.168.29.71/poc/getCourseContent.php"
        const val SERVER_URL_CURRICULUM = "http:/192.168.29.71/poc/getCurriculum.php"
        const val SERVER_URL_CURRICULUMDETAILS = "http:/192.168.29.71/poc/getCurriculumDetails.php"

    }
}