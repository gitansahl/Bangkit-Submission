package com.gitan.fundamentalfirstsubmission.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gitan.fundamentalfirstsubmission.databinding.FragmentFollowBinding
import com.gitan.fundamentalfirstsubmission.data.remote.response.User

class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding

    private val followViewModel by activityViewModels<FollowViewModel>()

    private var position: Int? = null
    private var username: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        followViewModel.getFollowData(username ?: "")

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUsers.layoutManager = layoutManager


        if (position == 1) {
            followViewModel.listFollower.observe(viewLifecycleOwner) {
                val adapter = UserAdapter(it)
                binding.rvUsers.adapter = adapter
                adapter.setOnItemCallback(object: UserAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: User) {
                        showSelectedUser(data)
                    }
                })
            }
        } else {
            followViewModel.listFollowing.observe(viewLifecycleOwner) {
                val adapter = UserAdapter(it)
                binding.rvUsers.adapter = adapter
                adapter.setOnItemCallback(object: UserAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: User) {
                        showSelectedUser(data)
                    }
                })
            }
        }
    }

    companion object {
        const val ARG_POSITION = "arg_position"
        const val ARG_USERNAME = "arg_username"
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) binding.progressCircular.visibility = View.VISIBLE
        else binding.progressCircular.visibility = View.INVISIBLE
    }

    private fun showSelectedUser(user: User) {
        val moveToDetailIntent = Intent(requireActivity(), DetailActivity::class.java)
        moveToDetailIntent.putExtra(DetailActivity.EXTRA_USER, user)
        startActivity(moveToDetailIntent)
        requireActivity().finish()
    }
}