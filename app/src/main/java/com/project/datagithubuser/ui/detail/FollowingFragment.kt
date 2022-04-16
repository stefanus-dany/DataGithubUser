package com.project.datagithubuser.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.datagithubuser.databinding.FragmentFollowingBinding
import com.project.datagithubuser.helper.Helper
import com.project.datagithubuser.helper.Helper.isLoading
import com.project.datagithubuser.service.response.UserResponse
import com.project.datagithubuser.ui.ViewModelFactory
import com.project.datagithubuser.ui.homepage.MainActivityAdapter

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding
    private lateinit var viewModel: DetailViewModel
    private lateinit var adapter: MainActivityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        viewModel = obtainViewModel(requireActivity())
        adapter = MainActivityAdapter {}

        val dataUser = activity?.intent?.getParcelableExtra<UserResponse>(Helper.EXTRA_USER)
        if (dataUser != null) {
            getDataFollowing(dataUser.login)
        }

        return binding?.root
    }

    private fun getDataFollowing(username: String) {
        binding?.progressBar?.isLoading(value = true)
        viewModel.getFollowing(username = username).observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) View.VISIBLE else View.GONE
            with(binding) {
                this?.tvEmpty?.visibility = View.GONE
                this?.rvFollowing?.adapter = adapter
                this?.rvFollowing?.layoutManager = LinearLayoutManager(requireContext())
            }
            binding?.progressBar?.isLoading(value = false)
            adapter.setData(it)

        }
    }

    private fun obtainViewModel(activity: FragmentActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}