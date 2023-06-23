package com.gitan.fundamentalfirstsubmission.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SwitchCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.gitan.fundamentalfirstsubmission.R
import com.gitan.fundamentalfirstsubmission.data.local.SettingPreferences
import com.gitan.fundamentalfirstsubmission.databinding.ActivityMainBinding
import com.gitan.fundamentalfirstsubmission.data.remote.response.User

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {

    private lateinit var bindingMainBinding: ActivityMainBinding
    private lateinit var switchLayout: View
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMainBinding.root)

        switchLayout = LayoutInflater.from(this).inflate(R.layout.switch_layout, null)
        val switchView = switchLayout.findViewById<SwitchCompat>(R.id.switch_dark_mode)

        val pref = SettingPreferences.getInstance(dataStore)
        mainViewModel.setPreferences(pref)
        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchView.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchView.isChecked = false
            }
        }
        switchView.setOnCheckedChangeListener{ _, isChecked: Boolean ->
            mainViewModel.saveThemeSetting(isChecked)
        }

        supportActionBar?.title = "Search Github User"

        val searchView = bindingMainBinding.searchUsername
        val layoutManager = LinearLayoutManager(this)
        bindingMainBinding.rvUsers.layoutManager = layoutManager
        bindingMainBinding.rvUsers.setHasFixedSize(true)

        mainViewModel.listUser.observe(this) {
            val adapter = UserAdapter(it)
            bindingMainBinding.rvUsers.adapter = adapter

            adapter.setOnItemCallback(object: UserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: User) {
                    showSelectedUser(data)
                }
            })
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) mainViewModel.findUser(query = query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            bindingMainBinding.progressCircular.visibility = View.VISIBLE
        } else {
            bindingMainBinding.progressCircular.visibility = View.GONE
        }
    }

    private fun showSelectedUser(user: User) {
        val moveToDetailIntent = Intent(this@MainActivity, DetailActivity::class.java)
        moveToDetailIntent.putExtra(DetailActivity.EXTRA_USER, user)
        startActivity(moveToDetailIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val darkModeItem = menu?.add(getString(R.string.dark_mode))
        darkModeItem?.actionView = switchLayout
        darkModeItem?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.ic_favorite) {
            val moveToFavoriteIntent = Intent(this@MainActivity, FavoriteUserActivity::class.java)
            startActivity(moveToFavoriteIntent)
        }
        return super.onOptionsItemSelected(item)
    }
}