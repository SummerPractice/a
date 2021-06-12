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
import com.example.polycareer.utils.isValidEmail
import com.example.polycareer.utils.isValidName
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
        else showError(it.errorMessage)
    }

    private fun showError(errorMessage: String) {
        if (errorMessage.isEmpty()) return
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun nextFragment() {
        val navController = NavHostFragment.findNavController(this)
        navController.navigate(R.id.action_signUpFragment_to_gradesMarksFragment)
        viewModel.navigationComplete()
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

        firstnameInput.setValidateRule { isValidName() }
        lastnameInput.setValidateRule { isValidName() }
        emailInput.setValidateRule { isValidEmail() }

        cbConf.setOnCheckedChangeListener { _: CompoundButton, _: Boolean ->
            cbConf.isValidConf()
        }
    }

    private fun EditText.setValidateRule(validator: EditText.() -> Boolean) {
        this.doOnTextChanged { _, _, _, _ -> this.validator() }
    }

    private fun allFieldsAreValid(): Boolean {
        var result = lastnameInput.isValidName()
        result = firstnameInput.isValidName() && result
        result = emailInput.isValidEmail() && result
        result = cbConf.isValidConf() && result
        return  result
    }

    override fun onClick(v: View?) {
        if (!allFieldsAreValid()) return

        val firstname = firstnameInput.text.toString()
        val lastname = lastnameInput.text.toString()
        val email = emailInput.text.toString()
        viewModel.saveUserDetail(firstname, lastname, email)
    }

    private fun EditText.isValidEmail(): Boolean {
        val result = this.text.toString().isValidEmail()
        error = if (!result) getString(R.string.invalid_email) else null
        return result
    }

    private fun EditText.isValidName(): Boolean {
        val result = this.text.toString().isValidName()
        error = if (!result) getString(R.string.invalid_name) else null
        return result
    }

    private fun CheckBox.isValidConf(): Boolean {
        this.error = if (!isChecked) getString(R.string.terms_error) else null
        return isChecked
    }
}