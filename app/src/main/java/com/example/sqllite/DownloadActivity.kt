package com.example.sqllite

import android.Manifest
import android.app.Dialog
import android.app.DownloadManager
import android.app.NotificationManager
import android.app.ProgressDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat


class DownloadActivity : AppCompatActivity() {

    private var downloadManager: DownloadManager? = null
    private var refid: Long = 0
    private var Download_Uri: Uri? = null
    var l_content_link: String? = null
    var l_content_id: String? = null
    var db: sqlite? = null
    var list: ArrayList<Long> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)
        l_content_link = intent.getStringExtra("CT_LINK")
        l_content_id = intent.getStringExtra("CT_ID")
        db = sqlite(this)


        downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        registerReceiver(onComplete,
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))


    }


    fun download(view: View?){

        Download_Uri = Uri.parse(l_content_link)
        val request = DownloadManager.Request(Download_Uri)
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setAllowedOverRoaming(false)
        request.setTitle("SMARTKAKSHA")
        request.setDescription("CONTENT")
        request.setVisibleInDownloadsUi(true)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/storage/emulated/0/Download/")
        refid = downloadManager!!.enqueue(request)

        Log.e("OUT", "" + refid);


        list.add(refid);
        db?.updateContentTable("/storage/emulated/0/Download/",l_content_id.toString())
    }

    fun isStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            true
        }
    }

    var onComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(ctxt: Context, intent: Intent) {
            val referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            Log.e("IN", "" + referenceId)
            list.remove(referenceId)
            if (list.isEmpty()) {
                Log.e("INSIDE", "" + referenceId)
                val mBuilder = NotificationCompat.Builder(this@DownloadActivity)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Download")
                        .setContentText("All Download completed")
                val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.notify(455, mBuilder.build())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(onComplete)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions!!, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {


            // permission granted
        }
    }





}

