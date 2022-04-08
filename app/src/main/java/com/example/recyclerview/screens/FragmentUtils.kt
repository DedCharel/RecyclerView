package com.example.recyclerview.screens

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recyclerview.App
import com.example.recyclerview.Navigator
import java.lang.IllegalStateException

typealias ViewModelCreator = (App) -> ViewModel?
class VIewModelFactory(
    private val app:App,
    private val viewModelCreator: ViewModelCreator = {null}
):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass){
            UserListViewModel::class.java ->{
                UserListViewModel(app.usersService)
            }
            else -> {
                viewModelCreator(app) ?: throw IllegalStateException("Unknown view model class")
            }
        }
        return viewModel as T
    }
}

fun Fragment.factory() = VIewModelFactory(requireContext().applicationContext as App)

fun Fragment.navigator() = requireActivity() as Navigator

inline fun <reified VM: ViewModel> Fragment.viewModelCreator(noinline creator: ViewModelCreator): Lazy<VM> {
    return viewModels { VIewModelFactory(requireContext().applicationContext as App, creator) }
}