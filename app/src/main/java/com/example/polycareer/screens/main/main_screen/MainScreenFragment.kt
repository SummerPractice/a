package com.example.polycareer.screens.main.main_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import com.example.polycareer.R
import org.koin.android.ext.android.inject


class MainScreenFragment : Fragment() {
    private lateinit var newTestButton: Button
    private lateinit var oldResultsButton: Button
    private lateinit var registerButton: Button

    private val viewModel: MainScreenViewModel by inject()
    private val stateObserver = Observer<MainScreenViewModel.MainScreenState> { state ->
        registerButton.visibility = if (state.isRegistered) View.GONE else View.VISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment__main__main_screen, container, false)

        newTestButton = rootView.findViewById(R.id.fragment__main__main_screen__new_test_bt)
        oldResultsButton = rootView.findViewById(R.id.fragment__main__main_screen__show_results_bt)
        registerButton = rootView.findViewById(R.id.fragment__main__main_screen__register_bt)

        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)
        viewModel.onCreateView()

        return rootView
    }
}