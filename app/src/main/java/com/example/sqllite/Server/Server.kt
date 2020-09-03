package com.example.sqllite

import android.R
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.io.*
import java.net.ServerSocket
import java.net.Socket
import java.text.SimpleDateFormat
import java.util.*

/*
Created by Divyanshu Gupta
In this we are connnecting teacher app to student app
 */
class Server : AppCompatActivity() {

    var  g_course_id: String?= null
    var  g_curriculum_id: String?= null
    var counter = 0
    var g_concept_id: String?= null
    var g_subconcept_id: String?= null
    var g_session_name: String?= null
    var g_session_id: String?= null
    var g_SP_NAME: TextView? = null

    //initializes all the private properties
    //For any server the ServerSocket and the Socket corresponding to the temp client
    // to be activated must be initialized
    private var serverSocket: ServerSocket? = null
    private var tempClientSocket: Socket?=null

    //here it sets the Thread initially to null
    var serverThread: Thread? = null

    //the msgList is initialized corresponding to the Linearlayout
    private var msgList: LinearLayout? = null
    private var handler: Handler? = null
    private var greenColor = 0
    private var edMessage: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.sqllite.R.layout.activity_server)

        val intent = intent



        g_course_id = intent.getStringExtra("co_id")
        g_concept_id = intent.getStringExtra("cn_id")
        g_subconcept_id = intent.getStringExtra("sc_id")
        g_session_name= intent.getStringExtra("sp_name")
        g_session_id= intent.getStringExtra("sp_id")
        g_curriculum_id = intent.getStringExtra("cu_id")


        //This sets the initial content view that would be displayed
        title = "Server-Side Endpoint"

        //initializes the identifier greenColor to be used anywhere within this file
        greenColor = ContextCompat.getColor(this,com.example.sqllite.R.color.green)

        //initializes a new handler for message queueing
        handler = Handler()
        msgList = findViewById(com.example.sqllite.R.id.msgList)
        edMessage = findViewById(com.example.sqllite.R.id.edMessage)

        msgList!!.removeAllViews()
        showMessage(g_session_name, Color.BLACK, false)

        //this initiates the serverthread defined later and starts the thread
        serverThread = Thread(ServerThread())
        serverThread!!.start()
        startTimeCounter()
    }

    //method to implement the different Textviews widget and display the message on
    //the Scrollview LinearLayout...
    fun textView(message: String?, color: Int, value: Boolean): TextView {

        //it checks if the message is empty then displays empty message
        var message = message
        if (null == message || message.trim { it <= ' ' }.isEmpty()) {
            message = "<Empty Message>"
        }
        val tv = TextView(this)
        tv.setTextColor(color)
        tv.text = "$message [$time]"
        tv.textSize = 20f
        tv.setPadding(0, 5, 0, 0)
        if (value) {
            tv.textAlignment = View.TEXT_ALIGNMENT_VIEW_END
        }
        return tv
    }

    //showMessage method to handle posting of mesage to the textView
    fun showMessage(message: String?, color: Int, value: Boolean) {
        handler!!.post { msgList!!.addView(textView(message, color, value)) }
    }

    //onClick method to handle clicking events whether to start up the  server or
    //send a message to the client
    fun onClick(view: View) {

        if (view.id == com.example.sqllite.R.id.send_data) {
            val msg = edMessage!!.text.toString().trim { it <= ' ' }
            showMessage("Teacher : $msg", Color.BLUE, false)
            if (msg.length > 0) {
                sendMessage(msg)
            }
            edMessage!!.setText("")
            return
        }
    }

    //method implemented to send message to the client
    private fun sendMessage(message: String) {
        try {
            if (null != tempClientSocket) {
                Thread(Runnable {
                    var out: PrintWriter? = null
                    try {
                        out = PrintWriter(BufferedWriter(
                                OutputStreamWriter(tempClientSocket!!.getOutputStream())),
                                true)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    out!!.println(message)
                }).start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /* serverthread method implemented here to activate the server network */
    internal inner class ServerThread : Runnable {
        override fun run() {
            var socket: Socket?
            try {
                serverSocket = ServerSocket(SERVER_PORT)

                //deactivates the visibility of the button
//               Button button = (Button) findViewById(R.id.start_server);
//               button.setVisibility(View.GONE);
            } catch (e: IOException) {
                e.printStackTrace()
                showMessage("Error Starting Server : " + e.message, Color.RED, false)
            }

            //communicates to client and displays error if communication fails
            if (null != serverSocket) {
                while (!Thread.currentThread().isInterrupted) {
                    try {
                        socket = serverSocket!!.accept()
                        val commThread = CommunicationThread(socket)
                        Thread(commThread).start()
                    } catch (e: IOException) {
                        e.printStackTrace()
                        showMessage("Error Communicating to Client :" + e.message, Color.RED, false)
                    }
                }
            }
        }
    }

    /* communicationThread class that implements the runnable class to communicate with the client */
    internal inner class CommunicationThread(private val clientSocket: Socket) : Runnable {
        private var input: BufferedReader? = null
        override fun run() {
            while (!Thread.currentThread().isInterrupted) {
                try {

                    //checks to see if the client is still connected and displays disconnected if disconnected
                    var read = input!!.readLine()
                    if (null == read || "Disconnect".contentEquals(read)) {
                        Thread.interrupted()
                        read = "Offline...."
                        showMessage("Client : $read", greenColor, true)
                        break
                    }
                    showMessage("Client : $read", greenColor, true)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        init {
            tempClientSocket = clientSocket
            try {
                input = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
            } catch (e: IOException) {
                e.printStackTrace()
                showMessage("Error Connecting to Client!!", Color.RED, false)
            }
            showMessage("Connected to Client!!", greenColor, true)
            sendMessage(g_curriculum_id+"/"+g_course_id+"/"+g_session_id)
        }
    }

    //getTime method implemented to format the date into H:m:s
    val time: String
        get() {
            val sdf = SimpleDateFormat("HH:mm:ss")
            return sdf.format(Date())
        }

    //personally described onDestroy method to disconnect from the network on destroy of the activity
    override fun onDestroy() {
        super.onDestroy()
        if (null != serverThread) {
            sendMessage("Disconnect")
            serverThread!!.interrupt()
            serverThread = null
        }
    }

    fun startTimeCounter() {
        val countTime: TextView = findViewById(com.example.sqllite.R.id.countTime)
        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countTime.text = g_session_name+" Starts in "+millisUntilFinished/1000

            }
            override fun onFinish() {
                countTime.text = "Finished"
                val intent = Intent(this@Server, CourseContent::class.java)
                intent.putExtra("co_id", g_course_id)
                intent.putExtra("sc_id", g_subconcept_id)
                intent.putExtra("cn_id", g_concept_id)
                startActivity(intent)
            }



        }.start()


    }

    fun Start_Session(view: View?){

        val intent = Intent(this@Server, CourseContent::class.java)
        intent.putExtra("co_id", g_course_id)
        intent.putExtra("sc_id", g_subconcept_id)
        intent.putExtra("cn_id", g_concept_id)
        startActivity(intent)

    }
    companion object {
        //the SERVER_PORT is initialized which must correspond to the port of the client
        const val SERVER_PORT = 5050
    }
}