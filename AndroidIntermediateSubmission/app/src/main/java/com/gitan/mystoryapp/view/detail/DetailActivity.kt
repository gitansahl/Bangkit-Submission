package com.gitan.mystoryapp.view.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.gitan.mystoryapp.data.model.Story
import com.gitan.mystoryapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        val story = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_STORY, Story::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_STORY)
        }

        if (story != null) {
            binding.tvDetailName.text = story.name
            binding.tvDetailDescription.text = story.description
            Glide.with(binding.root.context)
                .load(story.photoUrl)
                .into(binding.ivDetailPhoto)
        }
    }
}