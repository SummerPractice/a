package com.example.polycareer.screens.main.grades

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doOnTextChanged
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.polycareer.R
import com.example.polycareer.utils.isValidExamGrade
import com.example.polycareer.utils.isValidIdGrade

class GradesFragment : Fragment() {
    private lateinit var navController: NavController

    private lateinit var etMath: AppCompatEditText
    private lateinit var etPhys: AppCompatEditText
    private lateinit var etRus: AppCompatEditText
    private lateinit var etInf: AppCompatEditText
    private lateinit var etId: AppCompatEditText
    private lateinit var btnNext: AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment__main__grades, container, false)
        etMath = rootView.findViewById(R.id.fragment__main__grades__math_ev)
        etPhys = rootView.findViewById(R.id.fragment__main__grades__phys_ev)
        etRus = rootView.findViewById(R.id.fragment__main__grades__rus_ev)
        etInf = rootView.findViewById(R.id.fragment__main__grades__inf_ev)
        etId = rootView.findViewById(R.id.fragment__main__grades__id_ev)
        btnNext = rootView.findViewById(R.id.fragment__main__grades__next_btn)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        btnNext.setOnClickListener {
            onNextButtonClicked()
        }

        etMath.doOnTextChanged { _, _, _, _ ->
            validateExamGradeField(etMath)
        }

        etPhys.doOnTextChanged { _, _, _, _ ->
            validateExamGradeField(etPhys)
        }

        etRus.doOnTextChanged { _, _, _, _ ->
            validateExamGradeField(etRus)
        }

        etInf.doOnTextChanged { _, _, _, _ ->
            validateExamGradeField(etInf)
        }

        etId.doOnTextChanged { _, _, _, _ ->
            validateIdGradeField(etId)
        }

    }

    private fun onNextButtonClicked() {
        if (allFieldAreValid()) {
            // Отдать данные вью модели
            navController.navigate(R.id.action_gradesMarksFragment_to_quizItemFragment)
        }
    }

    private fun validateIdGradeField(editText: AppCompatEditText) {
        if (!isValidIdGrade(editText.text.toString())) {
            editText.error = getString(R.string.invalid_id_grade)
        }
    }

    private fun validateExamGradeField(editText: AppCompatEditText) {
        if (!isValidExamGrade(editText.text.toString())) {
            editText.error = getString(R.string.invalid_exam_grade)
        }
    }

    private fun allFieldAreValid() =
        isValidExamGrade(etMath.text.toString()) && isValidExamGrade(etPhys.text.toString()) &&
                isValidExamGrade(etInf.text.toString()) && isValidExamGrade(etRus.text.toString()) &&
                isValidIdGrade(etId.text.toString())

    companion object {
        @JvmStatic
        fun newInstance() = GradesFragment()
    }

}