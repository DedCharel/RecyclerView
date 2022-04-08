package com.example.recyclerview.model

import com.example.recyclerview.UserNotFoundException
import com.example.recyclerview.tasks.Task
import com.github.javafaker.Faker
import java.util.*
import kotlin.collections.ArrayList

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

    fun loadUsers(): List<User>{
        return users
    }

    fun getById(id: Long):UserDetails{
        val user = users.firstOrNull{it.id == id} ?: throw UserNotFoundException()
        return UserDetails(
            user = user,
            details = Faker.instance().lorem().paragraphs(3).joinToString("\n\n")
        )

    }

    fun deleteUser(user: User){
        val indexToDelete = findIndexById(user.id)
        if (indexToDelete != -1){
            users = ArrayList(users) // нужно пересозадать чтобы отобразилось (так как работаем с памятью, если получаем данный от куда-то то ненадо)
            users.removeAt(indexToDelete)
            notifyChanges()
        }
    }

    fun moveUser(user: User, moveBy: Int){
        val oldIndex = findIndexById(user.id)
        if (oldIndex == -1) return
        val newIndex = oldIndex + moveBy
        if (newIndex < 0 || newIndex >= users.size) return
        users = ArrayList(users)
        Collections.swap(users, oldIndex, newIndex)
        notifyChanges()
    }

    fun fireUser(user: User){
        val index = findIndexById(user.id)
        if (index == -1) return
        val updaterUser = users[index].copy(company = "")
        users = ArrayList(users)
        users[index] = updaterUser
        notifyChanges()
    }

    fun addListener(listener: UsersListener){
        listeners.add(listener)
        listener.invoke(users)
    }

    fun removeListener(listener: UsersListener){
        listeners.remove(listener)
    }

    private fun findIndexById(userId: Long): Int = users.indexOfFirst {it.id == userId}

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