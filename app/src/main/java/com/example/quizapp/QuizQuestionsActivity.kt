package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.quizapp.databinding.ActivityQuizQuestionsBinding

// Implement onClickListener in our class
class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityQuizQuestionsBinding
    private var mCurrentPosition: Int = 1
    private lateinit var mQuestionsList: List<Question>
    private var mSelectedOptionPosition: Int = 0
    private var correctAnswersCount: Int = 0
    private var mUserName: String? = null

    // Set the current view when close MainActivity view
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mUserName = intent.getStringExtra(Constants.EXTRA_USER_NAME)

        mQuestionsList = Constants.questions
        setQuestion()

        initClickListeners()
    }

    private fun initClickListeners() {
        binding.optionOne.setOnClickListener(this)
        binding.optionTwo.setOnClickListener(this)
        binding.optionThree.setOnClickListener(this)
        binding.optionFour.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)
    }

    // On click receive view for the current option on which is clicked
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.optionOne -> {
                selectedOptionView(binding.optionOne, 1)
            }
            R.id.optionTwo -> {
                selectedOptionView(binding.optionTwo, 2)
            }
            R.id.optionThree -> {
                selectedOptionView(binding.optionThree, 3)
            }
            R.id.optionFour -> {
                selectedOptionView(binding.optionFour, 4)
            } // Submit button functionality
            R.id.btnSubmit -> {
                // If no selected option do:
                onSubmitButtonClick()
            }
        }
    }

    private fun onSubmitButtonClick() {
        if (mSelectedOptionPosition == 0) {
            // move to next question on btnSubmit
            mCurrentPosition++

            when {
                mCurrentPosition <= mQuestionsList.size -> {
                    setQuestion()
                }
                else -> {
                    // Open intent for transfer data between the activities
                    val intent = Intent(this, ResultActivity::class.java)
                    intent.putExtra(Constants.EXTRA_USER_NAME, mUserName)
                    intent.putExtra(Constants.EXTRA_CORRECT_ANSWERS, correctAnswersCount)
                    intent.putExtra(Constants.EXTRA_TOTAL_QUESTIONS, mQuestionsList.size)
                    startActivity(intent)
                    finish()
                }
            }
            // If selected option do:
        } else {
            val question = mQuestionsList[mCurrentPosition - 1]

            if (question.correctAnswer != mSelectedOptionPosition) {
                markViewAsAnswered(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
            } else {
                correctAnswersCount++
            }
            markViewAsAnswered(question.correctAnswer, R.drawable.correct_option_border_bg)

            if (mCurrentPosition == mQuestionsList.size) {
                binding.btnSubmit.text = "Finish"
            } else {
                binding.btnSubmit.text = "GO TO NEXT QUESTION"
            }
            mSelectedOptionPosition = 0
        }
    }

    // Set Question with data
    private fun setQuestion() {

        // Get the current question
        val question = mQuestionsList[mCurrentPosition - 1]

        defaultOptionsView()
        if (mCurrentPosition == mQuestionsList.size) {
            binding.btnSubmit.text = "Finish"
        } else {
            binding.btnSubmit.text = "Submit"
        }

        binding.progressBar.progress = mCurrentPosition
        binding.tvProgress.text = "$mCurrentPosition / ${binding.progressBar.max}"

        binding.questionId.text = question.question
        binding.ivImage.setImageResource(question.image)
        binding.optionOne.text = question.optionOne
        binding.optionTwo.text = question.optionTwo
        binding.optionThree.text = question.optionThree
        binding.optionFour.text = question.optionFour
    }

    // Change the style of the options to default
    private fun defaultOptionsView() {
        // Create list of UI Elements like TextView and add them in to the list
        val options = ArrayList<TextView>()
        options.add(0, binding.optionOne)
        options.add(1, binding.optionTwo)
        options.add(2, binding.optionThree)
        options.add(3, binding.optionFour)

        // Set style to every option from the question
        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    // Change the style of the selected option
    private fun selectedOptionView(view: TextView, selectedOptionNumber: Int) {
        // Reset all buttons look
        defaultOptionsView()

        // Take the option position
        mSelectedOptionPosition = selectedOptionNumber

        // Set the look of the current option
        view.setTextColor(Color.parseColor("#363A43")) // Color
        view.setTypeface(view.typeface, Typeface.BOLD) // Text
        view.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg) // Background
    }

    // Assign
    private fun markViewAsAnswered(answerViewIndex: Int, drawableView: Int) {
        when (answerViewIndex) {
            1 -> binding.optionOne.background = ContextCompat.getDrawable(this, drawableView)
            2 -> binding.optionTwo.background = ContextCompat.getDrawable(this, drawableView)
            3 -> binding.optionThree.background = ContextCompat.getDrawable(this, drawableView)
            4 -> binding.optionFour.background = ContextCompat.getDrawable(this, drawableView)
        }
    }
}