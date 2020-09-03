@file:Suppress("DEPRECATION")

package com.example.sqllite

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util


/*Created By Divyanshu Gupta
This Activity is Created for PLAYING VIDEO(CLASSROOM SESSION) AND PERFORMING DIFFERENT FUNCTIONS LIKE
1) PLAYING NEXT VIDEO
2) STOPING THE SESSION
3) STARTING POP UP QUIZ

NOTE:- IN THIS ACTIVITY WE HAVE INTEGERATED A THIRD PARTY VIDEO PLAYER THAT IS EXOPLAYER(RECOMMENDED BY GOOGLE) FOR PLAYING VIDEO
*/

class ExoPlayer : AppCompatActivity() {
    var VIDEO: ArrayList<String>? = null

    var g_link: String? = null
    var g_link2: String? = null

    var g_content_id: String? = null

    var t1: TextView?=null
    var g_playerView: PlayerView? = null
    private var g_playWhenReady = true
    private var g_currentWindow = 0
    private var g_playbackPosition: Long = 0
    private var g_player: SimpleExoPlayer? = null
    var g_mydb3: sqlite? = null
    var extractoryFactory:ExtractorsFactory?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_playeer)
        g_playerView = findViewById(R.id.video_view)
        g_mydb3 = sqlite(this)

        val intent = intent
      //  g_content_id = intent.getStringExtra("video")

        VIDEO= intent.getStringArrayListExtra("video")
       g_link=VIDEO!!.get(0)
        g_link2=VIDEO!!.get(0)

                /*    val cursor1: Cursor = g_mydb3!!.getlink(g_content_id!!)
                    val l_stringBuilder1 = StringBuilder()
                    while (cursor1.moveToNext()) {
                        l_stringBuilder1.append(" " + cursor1.getString(0))
                        g_link = l_stringBuilder1.toString()
                        Toast.makeText(this@ExoPlayer, g_link, Toast.LENGTH_SHORT).show()}
*/


        extractoryFactory= DefaultExtractorsFactory()
    }


    private fun initializePlayer() //INTIALIZING THE PLAYER
    {
        g_player = ExoPlayerFactory.newSimpleInstance(this)
        g_playerView!!.player = g_player
        val uri = Uri.parse(g_link)
        val uri2 = Uri.parse(g_link2)

        val mediaSource = buildMediaSource(uri,uri2)
        g_player!!.playWhenReady = g_playWhenReady
        g_player!!.seekTo(g_currentWindow, g_playbackPosition)
        g_player!!.prepare(mediaSource, false, false)
    }

    private fun buildMediaSource(uri: Uri,uri2: Uri): MediaSource {

        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(this, "exoplayer-codelab")
        var firstsource= ProgressiveMediaSource.Factory(dataSourceFactory,extractoryFactory)
                .createMediaSource(uri)
        var secondsource= ProgressiveMediaSource.Factory(dataSourceFactory,extractoryFactory)
                .createMediaSource(uri2)
        return ConcatenatingMediaSource(firstsource, secondsource)//create a playlist
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



    fun pop(view: View?) {}//onclick function for  starting popup quiz
    fun stop(view: View?) {

        intent = Intent(this,Server::class.java)

        startActivity(intent)
    }//onclick function for  stopping the session


}




