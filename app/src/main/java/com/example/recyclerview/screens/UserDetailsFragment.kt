package com.example.recyclerview.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.recyclerview.R
import com.example.recyclerview.databinding.FragmentUserDetailsBinding

class UserDetailsFragment:Fragment() {

    private lateinit var binding: FragmentUserDetailsBinding

    private val userId get() = requireArguments().getLong(AGR_USER_ID)
    private val viewModel:UserDetailsViewModel by viewModelCreator {
        UserDetailsViewModel(it.usersService, userId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserDetailsBinding.inflate(layoutInflater,container, false)

        viewModel.userDetails.observe(viewLifecycleOwner, Observer {
            if (it.user.photo.isNotBlank()){
                Glide.with(this)
                    .load(it.user.photo)
                    .circleCrop()
                    .into(binding.photoImageView)
            } else {
                Glide.with(this)
                    .load(R.drawable.ic_user_avatar)
                    .into(binding.photoImageView)
            }
            binding.userDetailsTextView.text =it.details
        })
        binding.deleteButton.setOnClickListener{
            viewModel.deleteUser()
            navigator().toast(R.string.user_has_been_deleted)
            navigator().goBack()
        }
        return binding.root
    }

    companion object {
        private const val AGR_USER_ID = "AGR_USER_ID"

        fun newInstance(userId: Long): UserDetailsFragment{
            val fragment = UserDetailsFragment()
            fragment.arguments = bundleOf(AGR_USER_ID to userId)
            return fragment
        }
    }
}