package com.gitan.mystoryapp.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.gitan.mystoryapp.data.retrofit.ApiConfig
import com.gitan.mystoryapp.data.model.LoginData
import com.gitan.mystoryapp.LoginResponse
import com.gitan.mystoryapp.LoginResult
import com.gitan.mystoryapp.view.main.MainActivity
import com.gitan.mystoryapp.view.register.RegisterActivity
import com.gitan.mystoryapp.data.UserPreference
import com.gitan.mystoryapp.view.ViewModelFactory
import com.gitan.mystoryapp.view.main.dataStore
import com.gitan.mystoryapp.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        setupViewModel()
        setupAction()
    }

    private fun setupViewModel() {
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(this@LoginActivity)
        )[LoginViewModel::class.java]

        loginViewModel.getUser().observe(this) {
            if (it.isLogin) {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun showLoading (visible: Boolean) {
        binding.pbCircular.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {

            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            if (email.isEmpty()) {
                binding.emailEditTextLayout.error = "Masukkan email"
            } else if (password.isEmpty() || password.length < 8) {
                // Do nothing
            } else {
                val loginData = LoginData(email, password)
                loginAction(loginData)
            }
        }

        binding.registerButton.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    private fun loginAction(loginData: LoginData) {
        showLoading(true)
        val client = ApiConfig.getApiService().login(loginData)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body()?.error == false) {
                    showLoading(false)
                    Toast.makeText(this@LoginActivity, response.body()?.message, Toast.LENGTH_SHORT).show()
                    val loginResult: LoginResult? = response.body()?.loginResult
                    loginResult?.let {
                        loginViewModel.login(it)
                    }
                } else {
                    showLoading(false)
                    Toast.makeText(this@LoginActivity, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}