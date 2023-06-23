package com.gitan.fundamentalfirstsubmission.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gitan.fundamentalfirstsubmission.data.remote.response.User
import com.gitan.fundamentalfirstsubmission.databinding.ActivityFavoriteUserBinding

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite User"

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: FavoriteUserViewModel by viewModels{ factory }

        binding.rvUsers.apply {
            layoutManager = LinearLayoutManager(this@FavoriteUserActivity)
            setHasFixedSize(true)
        }
        viewModel.getFavoriteUser().observe(this) { favoriteUsers ->
            binding.progressCircular.visibility = View.GONE

            if (favoriteUsers.isEmpty()) binding.tvEmpty.visibility = View.VISIBLE
            else binding.tvEmpty.visibility = View.GONE

            val favoriteUsersList = arrayListOf<User>()
            favoriteUsers.map {
                val user = User(login = it.username, avatarUrl = it.avatarUrl ?: "")
                favoriteUsersList.add(user)
            }
            val adapter = UserAdapter(favoriteUsersList)
            binding.rvUsers.adapter = adapter
            adapter.setOnItemCallback(object: UserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: User) {
                    showSelectedUser(data)
                }
            })
        }

    }
    private fun showSelectedUser(user: User) {
        val moveToDetailIntent = Intent(this@FavoriteUserActivity, DetailActivity::class.java)
        moveToDetailIntent.putExtra(DetailActivity.EXTRA_USER, user)
        startActivity(moveToDetailIntent)
    }
}