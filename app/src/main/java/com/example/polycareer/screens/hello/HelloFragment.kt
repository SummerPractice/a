package com.example.polycareer.screens.hello

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.example.polycareer.App
import com.example.polycareer.R
import com.example.polycareer.utils.openScreen

class HelloFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment__auth__hello, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firstOpen()

        val beginButton = view.findViewById<AppCompatButton>(R.id.fragment__auth__hello_start_btn)
        beginButton.setOnClickListener { begin() }

        val registerButton = view.findViewById<AppCompatButton>(R.id.fragment__auth__hello_register_btn)
        registerButton.setOnClickListener { register() }
    }

    private fun register() {
        openScreen(R.id.action_helloFragment_to_signUpFragment)
    }

    private fun begin() {
        openScreen(R.id.action_helloFragment_to_mainFragment)
        openScreen(R.id.action_mainFragment_to_quizItemFragment)
    }

    private fun firstOpen() {
        val preferences =
            activity?.getSharedPreferences(App.IS_FIRST_OPEN, Context.MODE_PRIVATE) ?: return

        with(preferences.edit()) {
            putBoolean(App.FIRST_OPEN_KEY, false)
            apply()
        }
    }
}