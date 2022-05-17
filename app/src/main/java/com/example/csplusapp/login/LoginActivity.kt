package com.example.csplusapp.login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.csplusapp.main.MainActivity
import com.example.csplusapp.R
import com.example.csplusapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val mAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val progressDialog by lazy {
        ProgressDialog(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        clickLogin()
        clickSignUp()
    }

    private fun clickLogin() {
        binding.btnLogin.setOnClickListener {
            if (binding.lqEmail.text?.trim().toString().isNotEmpty()
                && binding.lgPassword.text?.trim().toString().isNotEmpty()
            ) {
                signInUser(
                    binding.lqEmail.text.toString(),
                    binding.lgPassword.text.toString()
                )
            } else {
                Toast.makeText(this, "Please fill all information!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun signInUser(user: String, pass: String) {
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        mAuth.signInWithEmailAndPassword(user, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    progressDialog.dismiss()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                } else {
                    progressDialog.dismiss()
                    Toast.makeText(
                        this,
                        "Incorrect account or password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun clickSignUp() {
        binding.tvSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }
    }
}