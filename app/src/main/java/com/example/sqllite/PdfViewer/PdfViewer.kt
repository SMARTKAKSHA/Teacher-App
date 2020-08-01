package com.example.sqllite


import android.content.Context
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import es.voghdev.pdfviewpager.library.RemotePDFViewPager
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter
import es.voghdev.pdfviewpager.library.remote.DownloadFile
import es.voghdev.pdfviewpager.library.util.FileUtil


class PdfViewer : AppCompatActivity(), DownloadFile.Listener {
    var g_link: String? = null

    var root: LinearLayout? = null
    var remotePDFViewPager: RemotePDFViewPager? = null


    var adapter: PDFPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_viewer)

        g_link = intent.getStringExtra("CT_LINK")



        root = findViewById<View>(R.id.remote_pdf_root) as LinearLayout



        setDownloadButtonListener()
    }
    override fun onDestroy() {
        super.onDestroy()
        adapter?.close()
    }
    protected fun setDownloadButtonListener() {
        val ctx: Context = this
        val listener: DownloadFile.Listener = this
            remotePDFViewPager = RemotePDFViewPager(ctx, g_link, listener)
            remotePDFViewPager!!.setId(R.id.pdfViewPager)

    }


    override fun onSuccess(url: String?, destinationPath: String?) {
        adapter = PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url))
        remotePDFViewPager?.setAdapter(adapter)
        updateLayout()
    }



    override fun onFailure(e: Exception?)
    {
        e?.printStackTrace()
    }

    override fun onProgressUpdate(progress: Int, total: Int) {


    }
    fun updateLayout() {
        root!!.removeAllViewsInLayout()


        root!!.addView(remotePDFViewPager,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)


    }
}