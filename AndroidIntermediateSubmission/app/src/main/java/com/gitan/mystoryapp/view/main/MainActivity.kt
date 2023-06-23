package com.gitan.mystoryapp.view.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gitan.mystoryapp.R
import com.gitan.mystoryapp.view.StoryAdapter
import com.gitan.mystoryapp.view.ViewModelFactory
import com.gitan.mystoryapp.databinding.ActivityMainBinding
import com.gitan.mystoryapp.view.LoadingStateAdapter
import com.gitan.mystoryapp.view.add_story.AddStoryActivity
import com.gitan.mystoryapp.view.login.LoginActivity
import com.gitan.mystoryapp.view.map.MapsActivity

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: StoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupAction()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(this@MainActivity)
        )[MainViewModel::class.java]

        mainViewModel.getUser().observe(this) {
            if (!it.isLogin) {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                this@MainActivity.finish()
            }
        }

        binding.rvStories.layoutManager = LinearLayoutManager(this)
        adapter = StoryAdapter()
        binding.rvStories.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        mainViewModel.stories.observe(this) {
            Log.d(TAG, "observed on stories")
            adapter.submitData(lifecycle, it)
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.refresh()
    }

    override fun onStart() {
        super.onStart()
        adapter.refresh()
    }

    private fun setupAction() {
        binding.addStoryButton.setOnClickListener {
            val intent = Intent(this@MainActivity, AddStoryActivity::class.java)
            startActivity(intent)
        }

        binding.mapsButton.setOnClickListener {
            val intent = Intent(this@MainActivity, MapsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.ic_logout) {
            mainViewModel.logout()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}