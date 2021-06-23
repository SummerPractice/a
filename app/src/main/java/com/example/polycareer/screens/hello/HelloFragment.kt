package com.example.polycareer.screens.hello

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.polycareer.App
import com.example.polycareer.R

class HelloFragment : Fragment(), View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment__auth__hello, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = view.findViewById<Button>(R.id.fragment__auth__hello_start_btn)
        button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val navController = findNavController(this)
        if (isUserExist()) {
            navController.navigate(R.id.action_helloFragment_to_quizItemFragment)
        } else {
            navController.navigate(R.id.action_helloFragment_to_signUpFragment)
        }
    }

    private fun isUserExist(): Boolean {
        val preferences = activity?.getSharedPreferences(App.CURRENT_USER_ID, Context.MODE_PRIVATE)
            ?: return false

        val userId = preferences.getLong(App.USER_ID_KEY, App.USER_ID_DEFAULT_VALUE)
        return userId != App.USER_ID_DEFAULT_VALUE
    }
}