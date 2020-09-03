package com.example.sqllite

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/*Created  By Divyanshu Gupta
This is the test activity for starting different quiz for the student


 */
class Test : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }

    //onclick function for starting test 1
    fun start_test1(view: View?)
    {
        val intent = Intent(this@Test, Test::class.java)
        startActivity(intent)
    }

    //onclick function for starting test 2
    fun start_test2(view: View?)
    {
        val intent = Intent(this@Test, Test::class.java)
        startActivity(intent)
    }

    //onclick function for starting test 3
    fun start_test3(view: View?)
    {
        val intent = Intent(this@Test, Test::class.java)
        startActivity(intent)
    }
    //creating option menu for logout
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(com.example.sqllite.R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val l_id = item.itemId
        if (l_id == com.example.sqllite.R.id.action_logout) {
            logout()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    //onclick for logging out from the teacher's account
    private fun logout()
    {
        val intent = Intent(this@Test, MainActivity::class.java)
        startActivity(intent)
    }
}