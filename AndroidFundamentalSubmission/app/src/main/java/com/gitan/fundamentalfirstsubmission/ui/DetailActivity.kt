package com.gitan.fundamentalfirstsubmission.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.gitan.fundamentalfirstsubmission.R
import com.gitan.fundamentalfirstsubmission.data.local.entity.FavoriteUser
import com.gitan.fundamentalfirstsubmission.databinding.ActivityDetailBinding
import com.gitan.fundamentalfirstsubmission.data.remote.response.User
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var detailBinding: ActivityDetailBinding

    companion object {
        val EXTRA_USER = "extra_user"
        @StringRes
        private val TAB_TITLE = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)
        supportActionBar?.title = "Detail Github User"

        val factory = ViewModelFactory.getInstance(this)
        val detailViewModel: DetailViewModel by viewModels { factory }

        val user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_USER, User::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_USER)
        }

        detailViewModel.getUserDetail(user?.login ?: "")

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.userData.observe(this) {
            detailBinding.tvUserName.text = it.login ?:"null"
            detailBinding.tvName.text = it.name ?: "null"
            detailBinding.tvFollower.text = String.format(getString(R.string.follower), it.followers)
            detailBinding.tvFollowing.text = String.format(getString(R.string.following), it.following)
            Glide.with(this)
                .load(it.avatarUrl)
                .into(detailBinding.ivProfile)
        }

        detailViewModel.isUserFavorited(user?.login ?: "").observe(this) {
            if (it) {
                detailBinding.btnFavorite.text = getString(R.string.remove_from_fav)
                detailBinding.btnFavorite.setOnClickListener {
                    detailViewModel.removeUser(user?.login ?: "")
                }
            } else {
                detailBinding.btnFavorite.text = getString(R.string.add_to_fav)
                detailBinding.btnFavorite.setOnClickListener {
                    val favoriteUser = FavoriteUser(user?.login ?: "", user?.avatarUrl)
                    detailViewModel.insertUser(favoriteUser)
                }
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = user?.login ?: ""
        val viewPager: ViewPager2 = detailBinding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = detailBinding.tabs
        TabLayoutMediator(tabs, viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TITLE[position])
        }.attach()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            detailBinding.progressCircular.visibility = View.VISIBLE
        } else {
            detailBinding.progressCircular.visibility = View.GONE
        }
    }
}