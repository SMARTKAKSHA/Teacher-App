package com.example.sqllite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class CourseContent : AppCompatActivity() {
    var g_course_id: String? = null
    var g_concept_id: String? = null
    var g_subconcept_id: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_content)
        var listView = findViewById<ListView>(R.id.listview)
        var db = sqlite(this)
        var intent = intent
        val l_concept_ID = intent.getStringExtra("cn_id")
        val l_course_ID = intent.getStringExtra("co_id")
        val l_subconcept_ID = intent.getStringExtra("sc_id")

        var l_content: ArrayList<String>
        l_content = db.displayContent(l_course_ID, l_concept_ID, l_subconcept_ID)


        var l_arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, l_content)
        listView.adapter = l_arrayAdapter
        listView.setOnItemClickListener { adapterView, view, position: Int, id: Long ->
            var l_content_link = l_content.get(position)
            var l_ct_id: String? = db.getContentId(l_content.get(position))
            var l_content_type: String? = db.getContentType(l_content.get(position))

            if (l_content_type == "VIDEO" || l_content_type == "AUDIO") {
                intent = Intent(this, ExoPlayer::class.java)
                intent.putExtra("CT_ID", l_ct_id)

                startActivity(intent)
                Toast.makeText(this@CourseContent, l_content_type, Toast.LENGTH_LONG).show()
            } else if (l_content_type == "WEBSITE") {
                intent = Intent(this, WebView::class.java)
                intent.putExtra("CT_ID", l_ct_id)

                startActivity(intent)
                Toast.makeText(this@CourseContent, l_content_type, Toast.LENGTH_LONG).show()

            }
            else if(l_content_type == "PDF"){
                intent = Intent(this, PdfViewer::class.java)
                intent.putExtra("CT_ID", l_ct_id)

                startActivity(intent)
                Toast.makeText(this@CourseContent, l_content_type, Toast.LENGTH_LONG).show()
            }
            else{

                Toast.makeText(this@CourseContent, "This media is not supported", Toast.LENGTH_LONG).show()
            }

        }
    }
}