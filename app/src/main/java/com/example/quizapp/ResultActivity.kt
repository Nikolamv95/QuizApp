package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quizapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(Constants.EXTRA_USER_NAME)
        val correctAnswers = intent.getIntExtra(Constants.EXTRA_CORRECT_ANSWERS, 0)
        val maxQuestions = intent.getIntExtra(Constants.EXTRA_TOTAL_QUESTIONS, 0)

        binding.tvName.text = username
        binding.tvScore.text = "Your score is $correctAnswers out of $maxQuestions"

        binding.btnFinish.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}