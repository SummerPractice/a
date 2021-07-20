package com.example.polycareer.screens.auth.grades

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
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
    private lateinit var pbLoading: ProgressBar
    private lateinit var errorScreen: View
    private lateinit var correctScreen: View

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
        when {
            state.errorMessage.isNotEmpty() -> showErrorScreen()
            state.isSaved -> toMenu()
            state.isLoading -> showLoading()
        }
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
        pbLoading = rootView.findViewById(R.id.fragment__main__grades__loading_pb)
        errorScreen = rootView.findViewById(R.id.grades_error_screen)
        setReloadButton()
        errorScreen.visibility = View.GONE
        correctScreen = rootView.findViewById(R.id.grades_correct)
        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object:
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (errorScreen.isVisible) {
                    toMenu()
                } else {
                    findNavController().navigateUp()
                }
            }
        })

        return rootView
    }

    private fun toMenu() {
        findNavController().popBackStack(R.id.mainFragment, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnNext.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        etMath.setValidateRule(viewModel) { onMathGradeChange(etMath.value) }
        etRus.setValidateRule(viewModel) { onRusGradeChange(etRus.value) }
        etPhys.setValidateRule(viewModel) { onPhysGradeChange(etPhys.value, etInf.value) }
        etInf.setValidateRule(viewModel) { onInfGradeChange(etPhys.value, etInf.value) }
        etId.setValidateRule(viewModel) { onIdGradeChange(etId.value) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        etMath.removeValidateRule()
        etRus.removeValidateRule()
        etPhys.removeValidateRule()
        etInf.removeValidateRule()
        etId.removeValidateRule()
    }

    override fun onClick(v: View?) {
        val math = etMath.value
        val rus = etRus.value
        val phys = etPhys.value
        val inf = etInf.value
        val id = etId.value
        viewModel.saveGrades(math, rus, phys, inf, id)
    }

    private fun setReloadButton() {
        val button = errorScreen.findViewById<AppCompatButton>(R.id.screen__result_error_btn)
        button.setOnClickListener {
            onClick(button)
        }
    }

    private fun showErrorScreen() {
        correctScreen.isVisible = false
        pbLoading.isVisible = false
        errorScreen.isVisible = true
    }

    private fun showLoading() {
        correctScreen.isVisible = false
        pbLoading.isVisible = true
        errorScreen.isVisible = false
    }
}