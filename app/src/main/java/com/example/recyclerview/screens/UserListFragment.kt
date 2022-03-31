package com.example.recyclerview.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerview.UserActionListener
import com.example.recyclerview.UsersAdapter
import com.example.recyclerview.databinding.FragmentUserListBinding
import com.example.recyclerview.model.User

class UserListFragment: Fragment() {
    private lateinit var binding: FragmentUserListBinding
    private lateinit var adapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserListBinding.inflate(inflater,container, false)
        adapter = UsersAdapter(object : UserActionListener{
            override fun onUserMove(user: User, moveBy: Int) {
                TODO("Not yet implemented")
            }

            override fun onUserDelete(user: User) {
                TODO("Not yet implemented")
            }

            override fun onUserDetails(user: User) {
                TODO("Not yet implemented")
            }

        })

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        return binding.root
    }
}