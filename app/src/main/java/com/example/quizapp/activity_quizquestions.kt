package com.example.quizapp

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quizquestions.*

class activity_quizquestions : AppCompatActivity(), View.OnClickListener {

    private var currentPosition:Int = 1 //used to go through the question list
    private var questionList:ArrayList<Question>? = null //the question list
    private var selectedOption: Int = 0 //user's selected option of the answers
    private var correctAnswers = 0 //correct answers counter
    private var userName:String? = null //username
    private var optionSelected:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quizquestions)
        userName = intent.getStringExtra(Constants.USERNAME)
        questionList = Constants.getQuestions()  // get the questions from Constants

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN


        setQuestion() // call the setQuestion funtion to implement the question properties on the views

        //set the click function for each button
        optionOne.setOnClickListener(this)
        optionTwo.setOnClickListener(this)
        optionThree.setOnClickListener(this)
        optionFour.setOnClickListener(this)
        submit.setOnClickListener(this)
    }

    override fun onBackPressed() {

        val dialogue = AlertDialog.Builder(this)
            dialogue.setMessage("You have not completed the quiz.\nAre you sure you want to quit?")
            dialogue.setCancelable(true)
            dialogue.setPositiveButton("Yes",DialogInterface.OnClickListener{ dialogInterface: DialogInterface, i: Int ->
                super.onBackPressed()
            })
            dialogue.setNegativeButton("No",null)
            dialogue.show()
   }

    //the question function
    private fun setQuestion(){
        val question: Question? = questionList!![currentPosition-1] //get the current question
        defaultOptionsView() //set the default view for options

        if(currentPosition == questionList!!.size+1){
            submit.text = "FINISH"
        }
        else{
            submit.text = "SUBMIT"
        }
        //assign the question variables to the views
        progressBar.progress = currentPosition
        progressno.text = "$currentPosition /"+ progressBar.max
        tvquestion.text = question!!.question
        country_image.setImageResource(question.image)
        optionOne.text =  question.optionOne
        optionTwo.text = question.optionTwo
        optionThree.text = question.optionThree
        optionFour.text = question.optionFour
        optionSelected = false
    }

    //the default option view function
    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()//create an array to put in all the option views
        options.add(0, optionOne)
        options.add(1, optionTwo)
        options.add(2, optionThree)
        options.add(3, optionFour)

        //set the default option properties
        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.button_background)
        }
    }

    //override the onClick function
    override fun onClick(v: View?) {
        //pass the option views to the selected options view to set the view
        when(v?.id){
            R.id.optionOne ->{
                optionSelected = true
                selectedOptionView(optionOne,1)
            }
            R.id.optionTwo ->{
                optionSelected = true
                selectedOptionView(optionTwo,2)
            }

            R.id.optionThree ->{
                optionSelected = true
                selectedOptionView(optionThree,3)
            }
            R.id.optionFour ->{
                optionSelected = true
                selectedOptionView(optionFour,4)
            }
            R.id.submit->{
                if(!optionSelected){
                    Toast.makeText(this,"Please select an option to continue",Toast.LENGTH_SHORT).show()
                }
                else{
                    if(selectedOption==0){
                        currentPosition++

                        when{
                            currentPosition <= questionList!!.size ->{
                                setQuestion()
                            }
                            else->{
                                val mintent = Intent(this, ResultActivity::class.java)
                                mintent.putExtra(Constants.USERNAME, userName)
                                mintent.putExtra(Constants.CORRECTANSWERS, correctAnswers)
                                mintent.putExtra(Constants.TOTALQUESTIONS, questionList!!.size)
                                startActivity(mintent)
                                finish()
                            }
                        }
                    }

                    else {
                        val question = questionList?.get(currentPosition - 1)
                        //optionSelected = true
                        if ((question!!.correctAnswer != selectedOption) and (submit.text.toString() == "SUBMIT")) {
                            answerView(selectedOption, R.drawable.wrong_option_background)
                        } else {
                            correctAnswers++
                        }

                        if (submit.text.toString() == "SUBMIT") {
                            answerView(question.correctAnswer, R.drawable.correct_option_background)
                            optionSelected = true
                        }

                        if (currentPosition == questionList!!.size) {
                            submit.text = "FINISH"
                        } else {
                            submit.text = "GO TO NEXT QUESTION"
                        }
                        selectedOption = 0 //reset selectedOption to 0

                    }
                }

            }

        }

    }


    //change the view of the selected option
    private fun selectedOptionView(tv:TextView, selectedOptionNumber:Int){
        //make sure the user has not already selected an answer
        if(submit.text.toString()=="SUBMIT") {
            defaultOptionsView()
            selectedOption = selectedOptionNumber
            tv.setTextColor(Color.parseColor("#363A43"))
            tv.setTypeface(tv.typeface, Typeface.BOLD)
            tv.background = ContextCompat.getDrawable(this, R.drawable.selected_button_background)
        }
        else{
            Toast.makeText(this,"Answer cannot be changed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun answerView(answer:Int , drawableView:Int){
        when(answer){
            1-> {optionOne.background = ContextCompat.getDrawable(this, drawableView)}
            2-> {optionTwo.background = ContextCompat.getDrawable(this, drawableView)}
            3-> {optionThree.background = ContextCompat.getDrawable(this, drawableView)}
            4-> {optionFour.background = ContextCompat.getDrawable(this, drawableView)}
        }
    }
}