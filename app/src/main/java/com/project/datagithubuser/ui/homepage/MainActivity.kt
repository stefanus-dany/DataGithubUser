package com.project.datagithubuser.ui.homepage

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.datagithubuser.R
import com.project.datagithubuser.databinding.ActivityMainBinding
import com.project.datagithubuser.helper.Helper
import com.project.datagithubuser.helper.Helper.isLoading
import com.project.datagithubuser.ui.ViewModelFactory
import com.project.datagithubuser.ui.detail.DetailActivity
import com.project.datagithubuser.ui.favorite.FavoriteActivity
import com.project.datagithubuser.ui.settings.SettingActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MainActivityAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = resources.getString(R.string.github_user)

        viewModel = obtainViewModel(this@MainActivity)

        adapter = MainActivityAdapter { selectedData ->
            val move = Intent(this@MainActivity, DetailActivity::class.java).apply {
                putExtra(Helper.EXTRA_USER, selectedData)
            }
            startActivity(move)
        }

        getBackgroundSearchView()

        getUserData()

        searchUser()
    }

    private fun getBackgroundSearchView() {
        binding.searchView.setBackgroundResource(
            when (resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_YES -> R.drawable.bg_search_dark
                Configuration.UI_MODE_NIGHT_NO -> R.drawable.bg_search
                else -> {
                    R.drawable.bg_search
                }
            }
        )
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MainViewModel::class.java]
    }

    private fun searchUser() {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setOnQueryTextListener(object :
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        binding.progressBar.isLoading(value = true)
                        viewModel.getSearchUsers(username = query)
                            .observe(this@MainActivity) { data ->
                                binding.tvEmpty.visibility =
                                    if (data.isNullOrEmpty()) View.VISIBLE else View.GONE
                                with(binding.rvMain) {
                                    layoutManager = LinearLayoutManager(this@MainActivity)
                                    adapter = this@MainActivity.adapter
                                }
                                adapter.setData(data)
                                binding.progressBar.isLoading(value = false)
                            }
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.isNullOrEmpty()) {
                        getUserData()
                        binding.tvEmpty.visibility = View.GONE
                    }
                    return true
                }

            })
        }
    }

    private fun getUserData() {
        binding.progressBar.isLoading(value = true)
        viewModel.getAllUsers().observe(this) {
            with(binding.rvMain) {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = this@MainActivity.adapter
                setHasFixedSize(true)
            }
            binding.progressBar.isLoading(value = false)
            adapter.setData(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_setting -> {
                startActivity(Intent(this@MainActivity, SettingActivity::class.java))
                true
            }
            R.id.menu_favorite -> {
                startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
                true
            }
            else -> true
        }
    }
}