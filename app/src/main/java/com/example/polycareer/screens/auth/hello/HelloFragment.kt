package com.example.polycareer.screens.auth.hello

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.polycareer.R

class HelloFragment : Fragment(), View.OnClickListener {
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment__auth__hello, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController(this)
        val button = view.findViewById<Button>(R.id.fragment__auth__hello_start_btn)
        button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        navController.navigate(R.id.action_helloFragment_to_signUpFragment)
    }

    companion object {
        @JvmStatic
        fun newInstance() = HelloFragment()
    }
}