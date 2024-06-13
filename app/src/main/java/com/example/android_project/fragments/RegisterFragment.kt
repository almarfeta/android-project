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

class RegisterFragment: Fragment() {
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_register, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val registerButton = view.findViewById<Button>(R.id.register_btn)
        registerButton.setOnClickListener { doRegister() }

        val goToLoginButton = view.findViewById<Button>(R.id.goto_login_btn)
        goToLoginButton.setOnClickListener { goToLogin() }
    }

    private fun doRegister() {
        val emailEditText = view?.findViewById<EditText>(R.id.et_register_email)
        val passwordEditText = view?.findViewById<EditText>(R.id.et_register_password)

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

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    getString(R.string.register_successful).showToast(context)
                    goToLogin()
                } else {
                    task.exception?.message?.showToast(context)
                }
            }
    }

    private fun goToLogin() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        findNavController().navigate(action)
    }
}