package com.gitan.mydicodingsubmission.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gitan.mydicodingsubmission.R
import com.gitan.mydicodingsubmission.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var profileBinding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(profileBinding.root)
    }
}