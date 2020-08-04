package com.example.sqllite

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class CourseContent : AppCompatActivity() {
    var alist: ArrayList<String>? = null
    var WEBSITE=ArrayList<String>(10)
    var PDF=ArrayList<String>(10)
    var VIDEO=  ArrayList<String>(10)
    var AUDIO=  ArrayList<String>(10)


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

        alist = ArrayList()
        alist!!.add("VIDEO")
        alist!!.add("PDF")
        alist!!.add("AUDIO")
        alist!!.add("WEBSITE")

      /*  var l_videocontent: ArrayList<String>
        l_videocontent = db.fetchVideoContent(l_course_ID, l_concept_ID, l_subconcept_ID)

        for (i in 0 until l_videocontent.size) {
            var link = l_videocontent.get(i)

            VIDEO!!.add(i,link)
        }
*/

        var size= l_content.size
        for (i in 0 until size) {
            var link= l_content.get(i)
            var l_content_type: String? = db.getContentType(link)
       if (l_content_type == "VIDEO") {
                VIDEO!!.add(link)
            }
            else if (l_content_type == "WEBSITE") {
                WEBSITE!!.add(link)

            }
            else if(l_content_type == "PDF"){
                PDF!!.add(link)
            }
          else if(l_content_type == "AUDIO"){
              AUDIO!!.add(link)
          }

        }


        var l_arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, alist!!)
        listView.adapter = l_arrayAdapter
        listView.setOnItemClickListener { adapterView, view, position: Int, id: Long ->
            var type= alist!!.get(position)

            if(type=="VIDEO"){
                intent = Intent(this, ExoPlayer::class.java)
                intent.putExtra("video",VIDEO)
                startActivity(intent)
            }
            else if(type=="PDF"){
                intent = Intent(this, PdfContent::class.java)
                intent.putExtra("pdf",PDF)
                startActivity(intent)
            }
            else if(type=="WEBSITE"){
                intent = Intent(this, WebContent::class.java)
                intent.putExtra("web",WEBSITE)
                startActivity(intent)
            }
            else if(type=="AUDIO"){
                intent = Intent(this, ExoPlayer::class.java)
                intent.putExtra("video",AUDIO)
                startActivity(intent)
            }
        }
/*

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

 */
    }
}
