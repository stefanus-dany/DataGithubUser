package com.project.datagithubuser.ui.detail

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.project.datagithubuser.R
import com.project.datagithubuser.database.Favorite
import com.project.datagithubuser.databinding.ActivityDetailBinding
import com.project.datagithubuser.helper.Helper
import com.project.datagithubuser.helper.Helper.EXTRA_USER
import com.project.datagithubuser.helper.Helper.isLoading
import com.project.datagithubuser.helper.Helper.loadImage
import com.project.datagithubuser.service.response.UserResponse
import com.project.datagithubuser.ui.SectionsPagerAdapter
import com.project.datagithubuser.ui.ViewModelFactory


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var user: UserResponse? = null
    private lateinit var viewModel: DetailViewModel
    private var fav: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = obtainViewModel(this@DetailActivity)
        user = intent.getParcelableExtra(EXTRA_USER)
        user?.login?.let { getDetailUser(it) }
        user?.let { it1 -> isFavorite(it1.login) }
        supportActionBar?.title = getString(R.string.username_format, user?.login)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getFavoriteButtonBackground()
        setupViewPager()

        binding.fabFav.setOnClickListener {
            if (!fav) {
                viewModel.insertFav(Favorite(username = user?.login, avatar = user?.avatarUrl))
                user?.let { it1 -> isFavorite(it1.login) }
                binding.fabFav.imageTintList =
                    ContextCompat.getColorStateList(this, android.R.color.holo_red_dark)
                Toast.makeText(
                    this,
                    resources.getString(R.string.add_favorite, "${user?.login}"),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                user?.login?.let { it1 -> viewModel.deleteFav(it1) }
                user?.let { it1 -> isFavorite(it1.login) }
                binding.fabFav.imageTintList =
                    ContextCompat.getColorStateList(this, android.R.color.white)
                Toast.makeText(
                    this,
                    resources.getString(R.string.un_add_favorite, "${user?.login}"),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun getFavoriteButtonBackground() {
        if (resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) {
            binding.fabFav.backgroundTintList =
                ContextCompat.getColorStateList(
                    this,
                    android.R.color.black
                )
        }
    }

    private fun isFavorite(username: String) {
        viewModel.isFav(username).observe(this) {
            fav = it != null
            binding.fabFav.imageTintList = if (!fav) ContextCompat.getColorStateList(
                this,
                android.R.color.white
            ) else ContextCompat.getColorStateList(this, android.R.color.holo_red_dark)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }

    private fun setupViewPager() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this@DetailActivity)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(Helper.TAB_TITLES[position])
        }.attach()
    }

    private fun getDetailUser(username: String) {
        binding.progressBar.isLoading(value = true)
        viewModel.getDetailUser(username = username).observe(this@DetailActivity) { data ->
            if (data != null) {
                with(binding) {
                    binding.tvEmpty.visibility = View.GONE
                    user?.let {
                        civProfileImage.loadImage(data.avatarUrl)
                        tvName.text = data.name
                        location.text = getString(R.string.getLocation, data.location)
                        repository.text =
                            getString(R.string.getRepository, data.login, data.repository)
                        company.text = getString(R.string.getCompany, data.company)
                        followers.text = getString(R.string.getFollowers, data.followers)
                        following.text = getString(R.string.getFollowing, data.following)
                    }
                }
            } else {
                binding.tvEmpty.visibility = View.VISIBLE
            }
            binding.progressBar.isLoading(value = false)

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> true
        }
    }
}