package com.example.flashcardstudyapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcardstudyapp.databinding.ActivityFlashcardBinding

class FlashcardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFlashcardBinding
    private val questions = arrayOf(
        "Nelson Mandela was the president of South Africa in 1994.",
        "The capital of France is London.",
        "The Earth is the third planet from the Sun.",
        "Water boils at 100 degrees Celsius at sea level.",
        "The Great Wall of China is visible from space with the naked eye."
    )
    private val answers = booleanArrayOf(true, false, true, true, false)
    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlashcardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displayQuestion()

        binding.trueButton.setOnClickListener { view ->
            animateButtonClick(view) {
                checkAnswer(true)
            }
        }

        binding.falseButton.setOnClickListener { view ->
            animateButtonClick(view) {
                checkAnswer(false)
            }
        }

        binding.nextButton.setOnClickListener { view ->
            animateButtonClick(view) {
                currentQuestionIndex++
                if (currentQuestionIndex < questions.size) {
                    displayQuestion()
                } else {
                    val intent = Intent(this, ScoreActivity::class.java).apply {
                        putExtra("SCORE", score)
                        putExtra("TOTAL_QUESTIONS", questions.size)
                        putExtra("QUESTIONS", questions)
                        putExtra("ANSWERS", answers)
                    }
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
                }
            }
        }
    }

    private fun animateButtonClick(view: View, action: () -> Unit) {
        val scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down)
        val scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up)

        view.startAnimation(scaleDown)
        scaleDown.setAnimationListener(object : android.view.animation.Animation.AnimationListener {
            override fun onAnimationStart(animation: android.view.animation.Animation?) {}
            override fun onAnimationRepeat(animation: android.view.animation.Animation?) {}
            override fun onAnimationEnd(animation: android.view.animation.Animation?) {
                view.startAnimation(scaleUp)
                action()
            }
        })
    }

    private fun displayQuestion() {
        binding.questionTextView.text = questions[currentQuestionIndex]
        binding.nextButton.isEnabled = false
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = answers[currentQuestionIndex]
        val message = if (userAnswer == correctAnswer) {
            score++
            "Correct!"
        } else {
            "Incorrect!"
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        binding.nextButton.isEnabled = true
    }
}