package com.project.datagithubuser.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.datagithubuser.database.Favorite
import com.project.datagithubuser.databinding.ListItemBinding
import com.project.datagithubuser.helper.Helper.loadImage
import com.project.datagithubuser.helper.NoteDiffCallback

class FavoriteAdapter(private val onItemClick: ((Favorite) -> Unit)) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    private var data = ArrayList<Favorite>()

    fun setData(data: List<Favorite>?) {
        if (data == null) return
        val diffCallback = NoteDiffCallback(this.data, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.data.clear()
        this.data.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item =
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = data[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(
        private val binding: ListItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Favorite) {
            with(binding) {
                tvName.text = data.username
                civProfileImage.loadImage(data.avatar)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick.invoke(data[adapterPosition])
            }
        }

    }
}