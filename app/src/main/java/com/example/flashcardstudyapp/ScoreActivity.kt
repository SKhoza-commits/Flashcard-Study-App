package com.example.flashcardstudyapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcardstudyapp.databinding.ActivityScoreBinding

class ScoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val score = intent.getIntExtra("SCORE", 0)
        val totalQuestions = intent.getIntExtra("TOTAL_QUESTIONS", 5)
        val questions = intent.getStringArrayExtra("QUESTIONS") ?: arrayOf()
        val answers = intent.getBooleanArrayExtra("ANSWERS") ?: booleanArrayOf()

        binding.scoreTextView.text = "Your score: $score/$totalQuestions"

        val feedback = when {
            score >= 4 -> "Excellent work! You're a star student!"
            score >= 3 -> "Good job! You're doing well!"
            else -> "Keep practicing! You'll improve with time!"
        }

        binding.feedbackTextView.text = feedback

        binding.reviewButton.setOnClickListener { view ->
            animateButtonClick(view) {
                val intent = Intent(this, ReviewActivity::class.java)
                intent.putExtra("QUESTIONS", questions)
                intent.putExtra("ANSWERS", answers)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }

        binding.exitButton.setOnClickListener { view ->
            animateButtonClick(view) {
                finishAffinity() // Closes all activities and exit the app
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
}