package com.example.android_project.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android_project.R
import com.example.android_project.utils.extenstions.showToast
import com.google.firebase.auth.FirebaseAuth

class LoginFragment: Fragment() {
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val loginButton = view.findViewById<Button>(R.id.login_btn)
        loginButton.setOnClickListener { doLogin() }

        val goToRegisterButton = view.findViewById<Button>(R.id.goto_register_btn)
        goToRegisterButton.setOnClickListener { goToRegister() }
    }

    private fun doLogin() {
        val emailEditText = view?.findViewById<EditText>(R.id.et_login_email)
        val passwordEditText = view?.findViewById<EditText>(R.id.et_login_password)

        val email = when(emailEditText?.text?.isNotEmpty()) {
            true -> emailEditText.text.toString()
            else -> {
                getString(R.string.invalid_email).showToast(context)
                return
            }
        }
        val password = when(passwordEditText?.text?.isNotEmpty()) {
            true -> passwordEditText.text.toString()
            else -> {
                getString(R.string.invalid_password).showToast(context)
                return
            }
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    getString(R.string.login_successful).showToast(context)
                    goToHome()
                } else {
                    task.exception?.message?.showToast(context)
                }
            }
    }

    private fun goToRegister() {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        findNavController().navigate(action)
    }

    private fun goToHome() {
        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        findNavController().navigate(action)
    }
}