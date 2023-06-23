package com.gitan.mydicodingsubmission.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gitan.mydicodingsubmission.R
import com.gitan.mydicodingsubmission.adapter.ListCastAdapter
import com.gitan.mydicodingsubmission.data.Cast
import com.gitan.mydicodingsubmission.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var rvCasts: RecyclerView
    private lateinit var mainBinding: ActivityMainBinding
    private val castList = ArrayList<Cast>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        rvCasts = mainBinding.rvCasts
        rvCasts.setHasFixedSize(true)

        castList.addAll(getListCasts())
        showRecyclerList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.about_page -> {
                val moveToProfileIntent = Intent(this@MainActivity, ProfileActivity::class.java)
                startActivity(moveToProfileIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showRecyclerList() {
        rvCasts.layoutManager = LinearLayoutManager(this)
        val listCastAdapter = ListCastAdapter(castList)
        rvCasts.adapter = listCastAdapter

        listCastAdapter.setOnItemCallback(object: ListCastAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Cast) {
                showSelectedCast(data)
            }
        })
    }


    private fun getListCasts(): Collection<Cast> {
        val listCast = ArrayList<Cast>()

        val dataNames = resources.getStringArray(R.array.real_names)
        val dataSeriesNames = resources.getStringArray(R.array.series_names)
        val dataDescriptions = resources.getStringArray(R.array.descriptions)
        val dataBirthDates = resources.getStringArray(R.array.birth_dates)
        val dataPhotos = resources.getStringArray(R.array.photos)

        for (i in dataNames.indices) {
            val cast = Cast(dataSeriesNames[i], dataNames[i], dataBirthDates[i], dataDescriptions[i], dataPhotos[i])
            listCast.add(cast)
        }

        return listCast
    }

    private fun showSelectedCast(cast: Cast) {
        val moveToDetailIntent = Intent(this@MainActivity, DetailActivity::class.java)
        moveToDetailIntent.putExtra(DetailActivity.EXTRA_CAST, cast)
        startActivity(moveToDetailIntent)
    }
}