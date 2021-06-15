package com.example.polycareer.screens.main.grades

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.example.polycareer.R
import com.example.polycareer.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class GradesFragment : Fragment(), View.OnClickListener {
    private lateinit var etMath: AppCompatEditText
    private lateinit var etRus: AppCompatEditText
    private lateinit var etPhys: AppCompatEditText
    private lateinit var etInf: AppCompatEditText
    private lateinit var etId: AppCompatEditText
    private lateinit var btnNext: AppCompatButton

    private val viewModel: GradesViewModel by viewModel()

    private val stateObserver = Observer<GradesViewModel.GradesState> { state ->
        etMath.error =
            setValueByCondition(state.isNotValidMath, getString(R.string.invalid_exam_grade))

        etRus.error =
            setValueByCondition(state.isNotValidRus, getString(R.string.invalid_exam_grade))

        etPhys.error =
            setValueByCondition(state.isNotValidPhys, getString(R.string.invalid_exam_grade))

        etInf.error =
            setValueByCondition(state.isNotValidInf, getString(R.string.invalid_exam_grade))

        etId.error =
            setValueByCondition(state.isNotValidId, getString(R.string.invalid_id_grade))

        if (state.isSaved) nextFragment()
        if (state.errorMessage.isNotEmpty()) showError(state.errorMessage)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment__main__grades, container, false)
        etMath = rootView.findViewById(R.id.fragment__main__grades__math_ev)
        etRus = rootView.findViewById(R.id.fragment__main__grades__rus_ev)
        etPhys = rootView.findViewById(R.id.fragment__main__grades__phys_ev)
        etInf = rootView.findViewById(R.id.fragment__main__grades__inf_ev)
        etId = rootView.findViewById(R.id.fragment__main__grades__id_ev)
        btnNext = rootView.findViewById(R.id.fragment__main__grades__next_btn)

        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)
        return rootView
    }

    private fun nextFragment() {
        val navController = NavHostFragment.findNavController(this)
        navController.navigate(R.id.action_gradesMarksFragment_to_quizItemFragment)
        viewModel.navigationComplete()
    }

    private fun showError(errorMessage: String) {
        if (errorMessage.isEmpty()) return
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnNext.setOnClickListener(this)
        etMath.setValidateRule(viewModel) { onMathGradeChange(etMath.value) }
        etRus.setValidateRule(viewModel) { onRusGradeChange(etRus.value) }
        etPhys.setValidateRule(viewModel) { onPhysGradeChange(etPhys.value) }
        etInf.setValidateRule(viewModel) { onInfGradeChange(etInf.value) }
        etId.setValidateRule(viewModel) { onIdGradeChange(etId.value) }
    }

    override fun onClick(v: View?) {
        val math = etMath.value
        val rus = etRus.value
        val phys = etPhys.value
        val inf = etInf.value
        val id = etId.value

        viewModel.saveGrades(math, rus, phys, inf, id)
    }
}