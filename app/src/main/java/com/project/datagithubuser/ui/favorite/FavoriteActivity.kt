package com.project.datagithubuser.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.datagithubuser.R
import com.project.datagithubuser.databinding.ActivityFavoriteBinding
import com.project.datagithubuser.helper.Helper
import com.project.datagithubuser.helper.Helper.isLoading
import com.project.datagithubuser.service.response.UserResponse
import com.project.datagithubuser.ui.ViewModelFactory
import com.project.datagithubuser.ui.detail.DetailActivity

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = obtainViewModel(this@FavoriteActivity)
        supportActionBar?.title = resources.getString(R.string.favorite_user)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = FavoriteAdapter { selectedData ->
            val move = Intent(this@FavoriteActivity, DetailActivity::class.java).apply {
                val tmp = selectedData.username?.let {
                    selectedData.avatar?.let { it1 ->
                        UserResponse(
                            it,
                            it1
                        )
                    }
                }
                putExtra(Helper.EXTRA_USER, tmp)
            }
            startActivity(move)
        }
        getFavData()
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

    private fun getFavData() {
        binding.progressBar.isLoading(value = true)
        viewModel.getAllFav().observe(this) {
            with(binding.rvMain) {
                layoutManager = LinearLayoutManager(this@FavoriteActivity)
                adapter = this@FavoriteActivity.adapter
            }
            binding.progressBar.isLoading(value = false)
            if (!it.isNullOrEmpty()) {
                adapter.setData(it)
                binding.rvMain.visibility = View.VISIBLE
                binding.tvEmpty.visibility = View.GONE
            } else {
                binding.rvMain.visibility = View.GONE
                binding.tvEmpty.visibility = View.VISIBLE
            }
        }
    }
}