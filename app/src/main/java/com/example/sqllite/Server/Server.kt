package com.example.sqllite

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.wifi.WifiManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_server.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket
import java.net.UnknownHostException
import java.nio.ByteBuffer
import java.nio.ByteOrder

/*Created By Divyanshu Gupta
This Activity is Created for hosting the session through this activity the connection b/w teacher and studemt will be established(classroom session)
 */
@SuppressLint("SetTextI18n")
class Server : AppCompatActivity() {
    var g_content_id:String?=null
    var g_content_id1:String?=null
    var g_session_name:String?=null

    var serverSocket: ServerSocket? = null
    var thread: Thread? = null
    var tvIP: TextView? = null
    var g_SP_NAME: TextView? = null

    var tvPort: TextView? = null
    var tvMessages: TextView? = null
    var etMessage: EditText? = null
    var btnSend: Button? = null
    var message: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server)

        val intent = intent

        g_content_id= intent.getStringExtra("ct_id")
        g_content_id1= intent.getStringExtra("ct_id1")
        g_session_name= intent.getStringExtra("sp_name")


        tvIP = findViewById(R.id.tvIP)
        tvPort = findViewById(R.id.tvPort)
        tvMessages = findViewById(R.id.tvMessages)
        etMessage = findViewById(R.id.etMessage)
        btnSend = findViewById(R.id.btnSend)
        g_SP_NAME = findViewById(R.id.SP_name)

        g_SP_NAME!!.text = g_session_name
        try {
            SERVER_IP = localIpAddress
        } catch (e: UnknownHostException) {
            e.printStackTrace()
        }
        thread = Thread(Thread1())
        thread!!.start()
        btnSend?.setOnClickListener(View.OnClickListener {
            message = etMessage?.getText().toString().trim { it <= ' ' }
            if (!message!!.isEmpty()) {
                Thread(Thread3(message!!)).start()
            }
        })
    }

    @get:Throws(UnknownHostException::class)
    private val localIpAddress: String
        private get() {
            val wifiManager = (applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager)
            val wifiInfo = wifiManager.connectionInfo
            val ipInt = wifiInfo.ipAddress
            return InetAddress.getByAddress(
                    ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(ipInt).array())
                    .hostAddress
        }

    private var output: PrintWriter? = null
    private var input: BufferedReader? = null

    internal inner class Thread1 : Runnable {
        override fun run() {
            val socket: Socket
            try {
                serverSocket = ServerSocket(SERVER_PORT)
                runOnUiThread {
                    tvConnectionStatus!!.text = "Not connected"
                    tvIP!!.text = "IP: $SERVER_IP"
                    tvPort!!.text = "Port: $SERVER_PORT"
                }
                try {
                    socket = serverSocket!!.accept()

                    output = PrintWriter(socket.getOutputStream())
                    input = BufferedReader(InputStreamReader(socket.getInputStream()))
                    runOnUiThread { tvConnectionStatus!!.text = "Connected\n" }
                    Thread(Thread2()).start()
                    val intent = Intent(this@Server, ExoPlayer::class.java)
                    intent.putExtra("ct_id",g_content_id)
                    intent.putExtra("ct_id1",g_content_id1)
                    startActivity(intent)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private inner class Thread2 : Runnable {
        override fun run() {
            while (true) {
                try {
                    val message = input!!.readLine()
                    if (message != null) {
                        runOnUiThread { tvMessages!!.append("client:$message\n") }
                    } else {
                        thread = Thread(Thread1())
                        thread!!.start()
                        return
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    internal inner class Thread3(private val message: String) : Runnable {
        override fun run() {
            output!!.write(message)
            output!!.flush()
            runOnUiThread {
                tvMessages!!.append("server: $message\n")
                etMessage!!.setText("")
            }
        }

    }

    companion object {
        var SERVER_IP = ""
        const val SERVER_PORT = 8080
    }
}
