package com.example.polycareer.screens.auth.sign_up

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.example.polycareer.R
import com.example.polycareer.utils.isValidEmail
import com.example.polycareer.utils.isValidName
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : Fragment(), View.OnClickListener {
    private lateinit var button: Button
    private lateinit var firstnameInput: EditText
    private lateinit var lastnameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var cbConf: AppCompatCheckBox
    private lateinit var cbNews: AppCompatCheckBox

    private val viewModel: SingUpViewModel by viewModel()

    private val stateObserver = Observer<SingUpViewModel.AuthState> {
        if (it.isSaved) nextFragment()
        else if (it.errorMessage.isNotEmpty()) Toast.makeText(context, it.errorMessage, Toast.LENGTH_SHORT).show()
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

        firstnameInput.doOnTextChanged { _, _, _, _ ->
            validateName(firstnameInput)
        }
        lastnameInput.doOnTextChanged { _, _, _, _ ->
            validateName(lastnameInput)
        }
        emailInput.doOnTextChanged { _, _, _, _ ->
            validateEmail(emailInput)
        }
        cbConf.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean ->
            validateConf(b)
        }
    }

    private fun validateEmail(editText: EditText) {
        if (!isValidEmail(editText.text.toString())) {
            editText.error = getString(R.string.invalid_email)
        } else editText.error = null
    }

    private fun validateName(editText: EditText) {
        if (!isValidName(editText.text.toString())) {
            editText.error = getString(R.string.invalid_name)
        } else editText.error = null
    }

    private fun validateConf(b: Boolean) {
        if (!b) cbConf.error = getString(R.string.terms_error)
        else cbConf.error = null
    }

    override fun onClick(v: View?) {
        if (allFieldsAreValid()) {
            val firstname = firstnameInput.text.toString()
            val lastname = lastnameInput.text.toString()
            val email = emailInput.text.toString()
            viewModel.saveUserDetail(firstname, lastname, email)
        }
    }

    private fun allFieldsAreValid(): Boolean {
        var result = true;
        if (!isValidEmail(emailInput.text.toString())) {
            emailInput.error = getString(R.string.invalid_email)
            result = false
        }
        if (!isValidName(firstnameInput.text.toString())) {
            firstnameInput.error = getString(R.string.invalid_name)
            result = false
        }
        if (!isValidName(lastnameInput.text.toString())) {
            lastnameInput.error = getString(R.string.invalid_name)
            result = false
        }
        if (!cbConf.isChecked) {
            cbConf.error = getString(R.string.terms_error)
            result = false
        }
        return result
    }

    private fun nextFragment() {
        val navController = NavHostFragment.findNavController(this)
        navController.navigate(R.id.action_signUpFragment_to_gradesMarksFragment)
        viewModel.navigationComplete()
    }
}