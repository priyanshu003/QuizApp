package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //remove status bar
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        buttonstart.setOnClickListener{
            if(username.text.toString().isEmpty()){
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            }
            else{
                val mintent = Intent(this, activity_quizquestions::class.java)
                mintent.putExtra(Constants.USERNAME, username.text.toString())
                startActivity(mintent)
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        username.setText("")
    }




}