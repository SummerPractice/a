package com.example.polycareer.screens.auth.sign_up

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.example.polycareer.R
import com.example.polycareer.utils.removeValidateRule
import com.example.polycareer.utils.setValidateRule
import com.example.polycareer.utils.setValueByCondition
import com.example.polycareer.utils.value
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : Fragment(), View.OnClickListener {
    private lateinit var button: AppCompatButton
    private lateinit var firstnameInput: AppCompatEditText
    private lateinit var lastnameInput: AppCompatEditText
    private lateinit var emailInput: AppCompatEditText
    private lateinit var cbConf: AppCompatCheckBox
    private lateinit var cbNews: AppCompatCheckBox

    private val viewModel: SingUpViewModel by viewModel()

    private val stateObserver = Observer<SingUpViewModel.AuthState> { state ->
        firstnameInput.error =
            setValueByCondition(state.isNotValidFirstName, getString(R.string.invalid_name))

        lastnameInput.error =
            setValueByCondition(state.isNotValidLastName, getString(R.string.invalid_name))

        emailInput.error =
            setValueByCondition(state.isNotValidEmail, getString(R.string.invalid_email))

        cbConf.error =
            setValueByCondition(state.isConfRuleNotExcepted, getString(R.string.terms_error))

        if (state.isSaved) nextFragment()
        if (state.errorMessage.isNotEmpty()) showError(state.errorMessage)
    }

    private fun nextFragment() {
        val navController = NavHostFragment.findNavController(this)
        navController.navigate(R.id.action_signUpFragment_to_gradesMarksFragment)
        viewModel.navigationComplete()
    }

    private fun showError(errorMessage: String) {
        if (errorMessage.isEmpty()) return
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment__auth__sign_up, container, false)
        firstnameInput = rootView.findViewById(R.id.fragment_auth__sign_up__firstname_et)
        lastnameInput = rootView.findViewById(R.id.fragment_auth__sign_up__lastname_et)
        emailInput = rootView.findViewById(R.id.fragment_auth__sign_up__email_et)
        button = rootView.findViewById(R.id.fragment_auth__sign_up__next_btn)
        cbConf = rootView.findViewById(R.id.fragment_auth__sign_up__terms_cb)
        cbNews = rootView.findViewById(R.id.fragment_auth__sign_up__email_cb)

        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button.setOnClickListener(this)

        cbConf.setOnCheckedChangeListener { _: CompoundButton, state: Boolean ->
            viewModel.onConfCheckedChange(state)
        }
    }

    override fun onResume() {
        super.onResume()
        firstnameInput.setValidateRule(viewModel) { onFirstNameChanged(firstnameInput.value) }
        lastnameInput.setValidateRule(viewModel) { onLastNameChanged(lastnameInput.value) }
        emailInput.setValidateRule(viewModel) { onEmailChanged(emailInput.value) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        firstnameInput.removeValidateRule()
        lastnameInput.removeValidateRule()
        emailInput.removeValidateRule()
    }

    override fun onClick(v: View?) {
        val firstname = firstnameInput.value
        val lastname = lastnameInput.value
        val email = emailInput.value
        val isConfChecked = cbConf.isChecked
        val isNewsChecked = cbNews.isChecked

        viewModel.saveUserDetail(firstname, lastname, email, isConfChecked, isNewsChecked)
    }
}