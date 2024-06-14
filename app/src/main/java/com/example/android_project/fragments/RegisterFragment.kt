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
import com.google.firebase.firestore.FirebaseFirestore

class RegisterFragment: Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_register, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val registerButton = view.findViewById<Button>(R.id.register_btn)
        registerButton.setOnClickListener { doRegister() }

        val goToLoginButton = view.findViewById<Button>(R.id.goto_login_btn)
        goToLoginButton.setOnClickListener { goToLogin() }
    }

    private fun doRegister() {
        val forenameEditText = view?.findViewById<EditText>(R.id.et_register_forename)
        val surnameEditText = view?.findViewById<EditText>(R.id.et_register_surname)
        val emailEditText = view?.findViewById<EditText>(R.id.et_register_email)
        val passwordEditText = view?.findViewById<EditText>(R.id.et_register_password)

        val forename = when(forenameEditText?.text?.isNotEmpty()) {
            true -> forenameEditText.text.toString()
            else -> {
                getString(R.string.invalid_forename).showToast(context)
                return
            }
        }
        val surname = when(surnameEditText?.text?.isNotEmpty()) {
            true -> surnameEditText.text.toString()
            else -> {
                getString(R.string.invalid_surname).showToast(context)
                return
            }
        }
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
                    val uid = auth.currentUser?.uid
                    if (uid != null) {
                        createNewUserDocument(uid, forename, surname)
                    }
                } else {
                    task.exception?.message?.showToast(context)
                }
            }
    }

    private fun createNewUserDocument(uid: String, forename: String, surname: String) {
        val userData = hashMapOf(
            "forename" to forename,
            "surname" to surname
        )
        db.collection("data").document(uid)
            .set(userData)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    getString(R.string.register_successful).showToast(context)
                    goToLogin()
                } else {
                    auth.currentUser?.delete()
                    task.exception?.message?.showToast(context)
                }
            }
    }

    private fun goToLogin() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        findNavController().navigate(action)
    }
}