package com.example.polycareer.screens.quiz.quiz_item

import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.polycareer.App
import com.example.polycareer.R
import com.google.android.material.progressindicator.LinearProgressIndicator
import org.koin.androidx.viewmodel.ext.android.viewModel


class QuizItemFragment : Fragment(), View.OnClickListener {
    private lateinit var rgAnswers: RadioGroup
    private lateinit var btnNext: AppCompatButton
    private lateinit var pbLoading: ProgressBar
    private lateinit var pbTest: LinearProgressIndicator

    private val viewModel: QuizItemViewModel by viewModel()

    private val stateObserver = Observer<QuizItemViewModel.QuizItemState> { state ->
        if (state.toResults != -1L) toResults(state.toResults)
        if (state.toMenu) toMenu()
        if (state.errorMessage.isEmpty()) {
            bindAnswers(state.answers)
            bindProgress(state.progress)
        } else {
            showToast(state.errorMessage)
        }
    }

    private fun bindProgress(progress: Int) {
        val progressAnimator = ObjectAnimator.ofInt(pbTest, "progress", pbTest.progress, progress)
        progressAnimator.duration = 1000
        progressAnimator.start()
        pbTest.progress = progress
    }

    private fun bindAnswers(answers: List<String>) {
        rgAnswers.removeAllViews()
        for (answer in answers) {
            val newRadioButton = AppCompatRadioButton(context)
            newRadioButton.setStyle()

            newRadioButton.id = View.generateViewId()
            newRadioButton.text = answer
            rgAnswers.addView(newRadioButton)
        }
        pbLoading.isVisible = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment__main__quiz_item, container, false)
        rgAnswers = rootView.findViewById(R.id.fragment__main__quiz_item__answers_rg)
        btnNext = rootView.findViewById(R.id.fragment__main__quiz_item__next_btn)
        pbLoading = rootView.findViewById(R.id.fragment__main__quiz_item__loading_pb)
        pbTest = rootView.findViewById(R.id.fragment__main__quiz_item__test_pb)

        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object:
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.toPreviousQuestion()
            }
        })

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnNext.setOnClickListener(this)

        viewModel.onFragmentCreated()
    }

    override fun onDestroy() {
        super.onDestroy()

        viewModel.onFragmentDestroyed()
    }

    override fun onClick(v: View?) {
        val radioButtonID: Int = rgAnswers.checkedRadioButtonId
        val radioButton: View? = rgAnswers.findViewById(radioButtonID)
        if (radioButton != null) {
            val selectedAnswer = rgAnswers.indexOfChild(radioButton)

            viewModel.onNextButtonClicked(selectedAnswer)
        }
    }

    private fun toResults(tryNumber: Long) {
        val bundle = Bundle()
        bundle.putLong(App.TRY_NUMBER, tryNumber)
        val navController = NavHostFragment.findNavController(this)
        navController.navigate(R.id.action_quizItemFragment_to_quizResultsFragment, bundle)
        viewModel.navigationComplete()
    }

    private fun toMenu() {
        findNavController().navigateUp()
    }

    private fun showToast(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }
}

private fun AppCompatRadioButton.setStyle() {
    val dp = context.resources.displayMetrics.density.toInt()
    buttonDrawable = ContextCompat.getDrawable(context, R.drawable.radio_button_inset)


    typeface = ResourcesCompat.getFont(context, R.font.opensans_regular)
    setTextSize(TypedValue.COMPLEX_UNIT_SP, 19f)
    setPadding(12 * dp, 12 * dp, 12 * dp, 12 * dp)
    background = ContextCompat.getDrawable(context, R.drawable.radio_button_background)

    val lp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
    lp.gravity = Gravity.CENTER
    lp.setMargins(25 * dp, 15 * dp, 30 * dp, 15 * dp)
    layoutParams = lp
}

