package com.gitan.mydicodingsubmission.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.gitan.mydicodingsubmission.R
import com.gitan.mydicodingsubmission.data.Cast
import com.gitan.mydicodingsubmission.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var detailBinding: ActivityDetailBinding

    companion object {
        const val EXTRA_CAST = "extra_cast"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(detailBinding.root)

        val cast = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_CAST, Cast::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_CAST)
        }

        if (cast != null) {
            detailBinding.tvBirthdateValue.text = cast.birthDate ?: "Unknown"
            detailBinding.tvRealName.text = cast.realName ?: "Unknown"
            detailBinding.tvDescriptionValue.text = cast.description ?: "Unknown"
            detailBinding.tvSeriesNameValue.text = cast.seriesName ?: "Unknown"
            Glide.with(detailBinding.root.context)
                .load(cast.photo)
                .into(detailBinding.ivPicture)
        }

        detailBinding.actionShare.setOnClickListener {
            val shareActionIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "${cast?.realName} play as ${cast?.seriesName} on Alice in Borderland")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(shareActionIntent, "Tell your friends!")
            startActivity(shareIntent)
        }

    }
}