package com.example.sqllite

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.MediaController
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource

import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_video_playeer.*
import java.io.File
import java.net.URI


/*Created By Divyanshu Gupta
This Activity is Created for PLAYING VIDEO(CLASSROOM SESSION) AND PERFORMING DIFFERENT FUNCTIONS LIKE
1) PLAYING NEXT VIDEO
2) STOPING THE SESSION
3) PAUSING THE SESSION
4) STARTING POP UP QUIZ

NOTE:- IN THIS ACTIVITY WE HAVE INTEGERATED A THIRD PARTY VIDEO PLAYER THAT IS EXOPLAYER(RECOMMENDED BY GOOGLE) FOR PLAYING VIDEO
*/

class ExoPlayer : AppCompatActivity() {
    var g_ct_id: String?=null
    var g_ct_id1: String?=null
    var g_link: String? = null
    var t1: TextView?=null
    var file:File?=null
    var g_playerView: PlayerView? = null
    private var g_playWhenReady = true
    private var g_currentWindow = 0
    private var g_playbackPosition: Long = 0
    private var g_player: SimpleExoPlayer? = null
    var g_mydb3: sqlite? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_playeer)
    g_playerView = findViewById(R.id.video_view)
        g_mydb3 = sqlite(this)

        val intent = intent
        g_ct_id = intent.getStringExtra("ct_id")
        g_ct_id1 = intent.getStringExtra("ct_id1")
t1= findViewById<TextView>(R.id.t1)
        val cursor1: Cursor = g_mydb3!!.getlink(g_ct_id!!)
        val l_stringBuilder1 = StringBuilder()
        while (cursor1.moveToNext()) {
            l_stringBuilder1.append(" " + cursor1.getString(0))
            g_link = l_stringBuilder1.toString()
            Toast.makeText(this@ExoPlayer, g_link, Toast.LENGTH_SHORT).show()

        }
 file= File(Environment.getExternalStorageDirectory().absolutePath+"/Download/Jazz_in_Paris.mp3")
t1!!.setText(file.toString())


    }


    private fun initializePlayer() //INTIALIZING THE PLAYER
    {
        g_player = ExoPlayerFactory.newSimpleInstance(this)
        g_playerView!!.player = g_player
        val uri = Uri.parse(file.toString())
        val mediaSource = buildMediaSource(uri)
        g_player!!.playWhenReady = g_playWhenReady
        g_player!!.seekTo(g_currentWindow, g_playbackPosition)
        g_player!!.prepare(mediaSource, false, false)
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(this, "exoplayer-codelab")
        return ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri)
    }

    public override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initializePlayer()
        }
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUi()
        if (Util.SDK_INT < 24 || g_player == null) {
            initializePlayer()
        }
    }

    public override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    public override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        g_playerView!!.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    private fun releasePlayer() {
        if (g_player != null) {
            g_playWhenReady = g_player!!.playWhenReady
            g_playbackPosition = g_player!!.currentPosition
            g_currentWindow = g_player!!.currentWindowIndex
            g_player!!.release()
            g_player = null
        }
    }



    fun pause(view: View?) {}//onclick function for  pausing the session
    fun pop(view: View?) {}//onclick function for  starting popup quiz
    fun stop(view: View?) {}//onclick function for  stopping the session

    fun nextvideo(view: View?) //onclick function for  playing next video
    {
        val l_intent = Intent(this@ExoPlayer, ExoPlayer::class.java)
        l_intent.putExtra("ct_id",g_ct_id1)
        startActivity(l_intent)
    }
}


