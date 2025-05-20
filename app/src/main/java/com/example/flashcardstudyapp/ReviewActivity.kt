package com.example.flashcardstudyapp

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcardstudyapp.databinding.ActivityReviewBinding

class ReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get questions and answers from intent
        val questions = intent.getStringArrayExtra("QUESTIONS") ?: arrayOf()
        val answers = intent.getBooleanArrayExtra("ANSWERS") ?: booleanArrayOf()

        // Build review text
        val reviewText = StringBuilder()
        for (i in questions.indices) {
            reviewText.append("Question ${i + 1}: ${questions[i]}\n")
            reviewText.append("Correct answer: ${if (answers[i]) "True" else "False"}\n\n")
        }
        binding.reviewTextView.text = reviewText.toString()

        // Restart quiz button with click animation
        binding.restartButton.setOnClickListener { view ->
            val scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down)
            val scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up)

            view.startAnimation(scaleDown)
            scaleDown.setAnimationListener(object : android.view.animation.Animation.AnimationListener {
                override fun onAnimationStart(animation: android.view.animation.Animation?) {}
                override fun onAnimationRepeat(animation: android.view.animation.Animation?) {}
                override fun onAnimationEnd(animation: android.view.animation.Animation?) {
                    view.startAnimation(scaleUp)
                    val intent = Intent(this@ReviewActivity, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
                }
            })
        }
    }
}