package com.example.recyclerview.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.recyclerview.UserNotFoundException
import com.example.recyclerview.model.User
import com.example.recyclerview.model.UserDetails
import com.example.recyclerview.model.UserService

class UserDetailsViewModel(
    private val userService: UserService,
    private val userId: Long
):ViewModel() {

    private val _userDetails = MediatorLiveData<UserDetails>()
    val userDetails: LiveData<UserDetails> = _userDetails

    init {
        loadUser()
    }

    fun loadUser(){
        if (_userDetails.value != null) return
        try {
           _userDetails.value = userService.getById(userId)
        } catch (e: UserNotFoundException){
            e.printStackTrace()
        }
    }

    fun deleteUser(){
        val userDetails = this.userDetails.value ?: return
        userService.deleteUser(userDetails.user)
    }
}