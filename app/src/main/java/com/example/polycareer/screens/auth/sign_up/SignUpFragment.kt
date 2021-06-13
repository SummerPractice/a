package com.example.polycareer.screens.auth.sign_up

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.example.polycareer.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : Fragment(), View.OnClickListener {
    private lateinit var button: Button
    private lateinit var firstnameInput: EditText
    private lateinit var lastnameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var cbConf: AppCompatCheckBox
    private lateinit var cbNews: AppCompatCheckBox

    private val viewModel: SingUpViewModel by viewModel()

    private val stateObserver = Observer<SingUpViewModel.AuthState> { state ->
        firstnameInput.error =
            if (state.isNotValidFirstName) getString(R.string.invalid_name) else null

        lastnameInput.error =
            if (state.isNotValidLastName) getString(R.string.invalid_name) else null

        emailInput.error = if (state.isNotValidEmail) getString(R.string.invalid_email) else null

        cbConf.error = if (state.isConfRuleNotExcepted) getString(R.string.terms_error) else null

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
        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)
        return inflater.inflate(R.layout.fragment__auth__sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firstnameInput = view.findViewById(R.id.fragment_auth__sign_up__firstname_et)
        lastnameInput = view.findViewById(R.id.fragment_auth__sign_up__lastname_et)
        emailInput = view.findViewById(R.id.fragment_auth__sign_up__email_et)

        button = view.findViewById(R.id.fragment_auth__sign_up__next_btn)
        button.setOnClickListener(this)

        cbConf = view.findViewById(R.id.fragment_auth__sign_up__terms_cb)
        cbNews = view.findViewById(R.id.fragment_auth__sign_up__email_cb)

        firstnameInput.setValidateRule { onFirstNameChanged(firstnameInput.value) }
        lastnameInput.setValidateRule { onLastNameChanged(lastnameInput.value) }
        emailInput.setValidateRule { onEmailChanged(emailInput.value) }

        cbConf.setOnCheckedChangeListener { _: CompoundButton, state: Boolean ->
            viewModel.onConfCheckedChange(state)
        }
    }

    override fun onClick(v: View?) {
        val firstname = firstnameInput.value
        val lastname = lastnameInput.value
        val email = emailInput.value
        val isConfChecked = cbConf.isChecked

        viewModel.saveUserDetail(firstname, lastname, email, isConfChecked)
    }

    private fun EditText.setValidateRule(validator: SingUpViewModel.() -> Unit) {
        this.doOnTextChanged { _, _, _, _ -> viewModel.validator() }
    }

    private val EditText.value: String
        get() = this.text.toString()
}