package com.example.polycareer.screens.main.main_screen

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.IdRes
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.example.polycareer.App
import com.example.polycareer.R
import com.example.polycareer.utils.openScreen
import org.koin.android.ext.android.inject


class MainScreenFragment : Fragment() {
    private lateinit var newTestButton: Button
    private lateinit var oldResultsButton: Button
    private lateinit var registerButton: Button

    private val viewModel: MainScreenViewModel by inject()
    private val stateObserver = Observer<MainScreenViewModel.MainScreenState> { state ->
        registerButton.visibility = if (state.isRegistered) View.GONE else View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openHelloScreen()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment__main__main_screen, container, false)

        newTestButton = rootView.findViewById(R.id.fragment__main__main_screen__new_test_bt)
        newTestButton.setOnClickListener {
            viewModel.createDefaultUser()
            openTest()
        }

        oldResultsButton = rootView.findViewById(R.id.fragment__main__main_screen__show_results_bt)
        oldResultsButton.setOnClickListener { showResults() }

        registerButton = rootView.findViewById(R.id.fragment__main__main_screen__register_bt)
        registerButton.setOnClickListener { register() }

        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)
        viewModel.onCreateView()

        return rootView
    }

    private fun openTest() {
        openScreen(R.id.action_mainFragment_to_quizItemFragment)
    }

    private fun showResults() {
        openScreen(R.id.action_mainFragment_to_oldResultsFragment)
    }

    private fun register() {
        openScreen(R.id.action_mainFragment_to_signUpFragment)
    }

    private fun openHelloScreen() {
        val preferences = activity?.getSharedPreferences(App.IS_FIRST_OPEN, Context.MODE_PRIVATE) ?: return
        val isFirstOpen = preferences.getBoolean(App.FIRST_OPEN_KEY, true)

        if (isFirstOpen)
            openScreen(R.id.action_mainFragment_to_helloFragment)
    }
}