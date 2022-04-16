package com.project.datagithubuser.ui.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.datagithubuser.databinding.ListItemBinding
import com.project.datagithubuser.helper.Helper.loadImage
import com.project.datagithubuser.service.response.UserResponse

class MainActivityAdapter(private val onItemClick: ((UserResponse) -> Unit)) :
    RecyclerView.Adapter<MainActivityAdapter.ViewHolder>() {
    private var data = ArrayList<UserResponse>()

    fun setData(data: List<UserResponse>?) {
        if (data == null) return
        this.data.clear()
        this.data.addAll(data)
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

        fun bind(data: UserResponse) {
            with(binding) {
                tvName.text = data.login
                civProfileImage.loadImage(data.avatarUrl)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick.invoke(data[adapterPosition])
            }
        }

    }
}