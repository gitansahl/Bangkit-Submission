package com.gitan.mystoryapp.view.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.gitan.mystoryapp.data.retrofit.ApiConfig
import com.gitan.mystoryapp.data.response.GeneralResponse
import com.gitan.mystoryapp.data.model.RegisterData
import com.gitan.mystoryapp.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAction()
    }

    private fun setAction() {
        binding.loginButton.setOnClickListener {
            finish()
        }

        binding.registerButton.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()

            if (email.isEmpty()) {
                binding.emailEditTextLayout.error = "Masukkan email"
            } else if (name.isEmpty()){
                binding.emailEditTextLayout.error = "Masukkan nama"
            } else {
                val registerData = RegisterData(name, email, password)
                registerAction(registerData)
            }
        }
    }

    private fun registerAction(registerData: RegisterData) {
        showLoading(true)
        val client = ApiConfig.getApiService().registerUser(registerData)
        client.enqueue(object : Callback<GeneralResponse> {
            override fun onResponse(
                call: Call<GeneralResponse>,
                response: Response<GeneralResponse>
            ) {
                showLoading(false)
                if (response.body() != null && response.body()?.error == false) {
                    Toast.makeText(this@RegisterActivity, response.body()?.message, Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@RegisterActivity, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@RegisterActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showLoading (visible: Boolean) {
        binding.pbCircular.visibility = if (visible) View.VISIBLE else View.GONE
    }
}