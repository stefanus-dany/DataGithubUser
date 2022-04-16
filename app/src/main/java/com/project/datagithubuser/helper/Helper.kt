package com.project.datagithubuser.helper

import android.content.Context
import android.view.View
import androidx.annotation.StringRes
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.project.datagithubuser.R
import de.hdodenhof.circleimageview.CircleImageView

object Helper {

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    const val EXTRA_USER = "extra_user"
    const val START_ALPHA_SPLASH = 0f
    const val END_ALPHA_SPLASH = 1f
    const val DURATION_SPLASH = 1500L
    const val TAG = "log"

    @StringRes
    val TAB_TITLES = intArrayOf(
        R.string.tab_title_following,
        R.string.tab_title_followers
    )

    fun CircleImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .apply(RequestOptions().override(300, 300))
            .centerCrop()
            .into(this)
    }

    fun View.isLoading(value: Boolean) {
        this.visibility = if (value) View.VISIBLE else View.GONE
    }
}