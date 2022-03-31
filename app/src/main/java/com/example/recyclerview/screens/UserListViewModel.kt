package com.example.recyclerview.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recyclerview.model.User
import com.example.recyclerview.model.UserService
import com.example.recyclerview.model.UsersListener

class UserListViewModel(
    private val userService:UserService
):ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    init {
        loadUsers()
    }

    override fun onCleared() {
        super.onCleared()
        userService.removeListener(listener)
    }

    fun loadUsers(){
        userService.addListener(listener)
    }

    fun moveUser(user: User, moveBy: Int){
        userService.moveUser(user, moveBy)
    }

    fun deleteUser(user: User){
        userService.deleteUser(user)
    }

    private val listener: UsersListener = {
        _users.value = it
    }
}