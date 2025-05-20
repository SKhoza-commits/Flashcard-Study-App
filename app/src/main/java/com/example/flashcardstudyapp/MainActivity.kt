package com.example.flashcardstudyapp

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcardstudyapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startButton.setOnClickListener { view ->
            // Scale animation on click
            val scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down)
            val scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up)

            view.startAnimation(scaleDown)
            scaleDown.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    view.startAnimation(scaleUp)
                    val intent = Intent(this@MainActivity, FlashcardActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
            })
        }
    }
}