package com.example.polycareer.screens.auth.sign_up

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.example.polycareer.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : Fragment(), View.OnClickListener {
    private lateinit var button: Button
    private lateinit var firstnameInput: EditText
    private lateinit var lastnameInput: EditText
    private lateinit var emailInput: EditText

    private val viewModel: SingUpViewModel by viewModel()

    private val stateObserver = Observer<SingUpViewModel.AuthState> {
        if (it.isSaved) nextFragment()
        else Toast.makeText(context, it.errorMessage, Toast.LENGTH_SHORT).show()
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
    }

    override fun onClick(v: View?) {
        val firstname = firstnameInput.text.toString()
        val lastname = lastnameInput.text.toString()
        val email = emailInput.text.toString()

        viewModel.saveUserDetail(firstname, lastname, email)
    }

    private fun nextFragment() {
        val navController = NavHostFragment.findNavController(this)
        navController.navigate(R.id.action_signUpFragment_to_gradesMarksFragment)
        viewModel.navigationComplete()
    }
}