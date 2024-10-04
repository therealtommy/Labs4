package com.example.lab4


import android.health.connect.datatypes.units.Length
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import android.content.Intent

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView
    private lateinit var cheatButton: Button
    var i = 1
    var score = 0
    private val quizViewModel: QuizViewModel by
    lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val currentIndex =
            savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex



        val provider: ViewModelProvider = ViewModelProvider(this)
        val quizViewModel =
            provider.get(QuizViewModel::class.java)
        Log.d(TAG, "Got a QuizViewModel:$quizViewModel")

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)
        cheatButton=findViewById(R.id.cheat_button)

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
            trueButton.setVisibility(View.INVISIBLE)
            falseButton.setVisibility(View.INVISIBLE)
        }
        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
            falseButton.setVisibility(View.INVISIBLE)
            trueButton.setVisibility(View.INVISIBLE)
        }
        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
            trueButton.setVisibility(View.VISIBLE)
            falseButton.setVisibility(View.VISIBLE)
            Log.d(TAG, i.toString())
            i = i + 1
            if (i == 6)
                nextButton.setVisibility(View.INVISIBLE)
        }
        updateQuestion()
        cheatButton.setOnClickListener()
        {
            val answerIsTrue=quizViewModel.currentQuestionAnswer
            val intent=CheatActivity.newIntent(this@MainActivity,answerIsTrue)
            startActivity(intent)
        }

    }
    override fun onStart() {
        super.onStart()
        Log.d(TAG,
            "onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG,
            "onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG,
            "onPause() called")
    }
    override fun onSaveInstanceState(savedInstanceState: Bundle)
    {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG,
            "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,
            "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId =
            quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)


    }
    private fun checkAnswer(userAnswer: Boolean)
    {

        val correctAnswer = quizViewModel.currentQuestionAnswer
       if (userAnswer == correctAnswer) {
            score = score + 1
        } else {
            score = score
        }
        if (i == 6)
            Toast.makeText(this,"Ваш счет: " + score.toString(), Toast.LENGTH_LONG).show()

    }

}