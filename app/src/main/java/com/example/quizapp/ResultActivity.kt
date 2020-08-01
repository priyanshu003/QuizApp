package com.example.quizapp

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        name.text = intent.getStringExtra(Constants.USERNAME)
        val correctAnswers = intent.getIntExtra(Constants.CORRECTANSWERS, 0)
        val totalscore = intent.getIntExtra(Constants.TOTALQUESTIONS, 10)

        score.text = "Your score is $correctAnswers out of $totalscore"

        buttonfinish.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    override fun onBackPressed() {

        val dialogue = AlertDialog.Builder(this)
        dialogue.setMessage("Are you sure you want to quit?")
        dialogue.setCancelable(true)
        dialogue.setPositiveButton("Yes",
            DialogInterface.OnClickListener{ dialogInterface: DialogInterface, i: Int ->
            super.onBackPressed()
        })
        dialogue.setNegativeButton("No",null)
        dialogue.show()
    }
}