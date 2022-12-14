package io.denix.project.universaltunnel.ui.user

import android.content.res.AssetManager
import android.util.Log
import androidx.lifecycle.*
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
    lateinit var usersLiveData: LiveData<List<User>>
    private val dispatcher = Dispatchers.Default

    init {
        val fakeNetworkDataSource = FakeNetworkDataSource(dispatcher, Json, assetManager)
        userRepository = FakeNetworkUserRepository(dispatcher, fakeNetworkDataSource)
    }

    fun onViewReady() {
        fetchAllUsers()
    }

    private fun fetchAllUsers() {
        viewModelScope.launch(dispatcher) {
            userRepository.getUsers().collect { userList ->
                userList.map { user ->
                    Log.d("userRepository", user.toString())
                }
            }
//            usersLiveData = userRepository.getUsers().asLiveData()
        }
    }
}