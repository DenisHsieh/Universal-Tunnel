package io.denix.project.universaltunnel.ui.user

import android.content.res.AssetManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.denix.project.universaltunnel.data.external.User
import io.denix.project.universaltunnel.data.user.repository.FakeNetworkUserRepository
import io.denix.project.universaltunnel.data.user.repository.UserRepository
import io.denix.project.universaltunnel.network.fake.FakeNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class UserViewModel(
    assetManager: AssetManager
) : ViewModel() {

    private var userRepository: UserRepository
    lateinit var usersLiveData: MutableLiveData<List<User>>
    private val ioDispatcher = Dispatchers.IO

    init {
        val fakeNetworkDataSource = FakeNetworkDataSource(ioDispatcher, Json, assetManager)
        userRepository = FakeNetworkUserRepository(ioDispatcher, fakeNetworkDataSource)
    }

    fun onViewReady() {
        fetchAllUsers()
    }

    private fun fetchAllUsers() {
        viewModelScope.launch(ioDispatcher) {
            userRepository.getUsers().collect { userList ->
                userList.map { user ->
                    Log.d("userRepository", user.toString())
                }
            }
        }
    }
}