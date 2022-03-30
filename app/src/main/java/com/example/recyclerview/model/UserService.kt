package com.example.recyclerview.model

import com.github.javafaker.Faker
import java.util.*

typealias UsersListener = (users: List<User>) -> Unit

class UserService {
    private var users = mutableListOf<User>()

    private val listeners = mutableSetOf<UsersListener>()

    init {
        val faker = Faker.instance()
        IMAGES.shuffle() //перемешаем картинки
        users = (1..100).map { User(
            id = it.toLong(),
            name = faker.name().name(),
            company = faker.company().name(),
            photo = IMAGES[it % IMAGES.size]
        ) }.toMutableList()
    }

    fun getUsers(): List<User>{
        return users
    }

    fun deleteUser(user: User){
        val indexDelete = users.indexOfFirst {it.id == user.id}
        if (indexDelete != -1){
            users.removeAt(indexDelete)
            notifyChanges()
        }
    }

    fun moveUser(user: User, moveBy: Int){
        val oldIndex = users.indexOfFirst { it.id == user.id}
        if (oldIndex == -1) return
        val newIndex = oldIndex + moveBy
        if (newIndex < 0 || newIndex >= users.size) return
        Collections.swap(users, oldIndex, newIndex)
        notifyChanges()
    }

    fun addListener(listener: UsersListener){
        listeners.add(listener)
        listener.invoke(users)
    }

    fun removeListener(listener: UsersListener){
        listeners.remove(listener)
    }

    private fun notifyChanges(){
        listeners.forEach{it.invoke(users)}
    }

    companion object{
        private val IMAGES = mutableListOf(
            "https://android-obzor.com/wp-content/uploads/2022/02/1-6.jpg",
            "https://android-obzor.com/wp-content/uploads/2022/02/2-6.jpg",
            "https://android-obzor.com/wp-content/uploads/2022/02/3-6.jpg",
            "https://android-obzor.com/wp-content/uploads/2022/02/4-6.jpg",
            "https://android-obzor.com/wp-content/uploads/2022/02/5-6.jpg",
            "https://android-obzor.com/wp-content/uploads/2022/02/6-6.jpg",
            "https://android-obzor.com/wp-content/uploads/2022/02/8-6.jpg"
        )
    }
}