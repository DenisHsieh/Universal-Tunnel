package io.denix.project.universaltunnel.ui.user

import android.content.res.AssetManager
import android.util.Log
import androidx.lifecycle.*
import io.denix.project.universaltunnel.data.external.User
import io.denix.project.universaltunnel.data.user.model.UserDao
import io.denix.project.universaltunnel.data.user.repository.FakeNetworkUserRepository
import io.denix.project.universaltunnel.data.user.repository.UserRepository
import io.denix.project.universaltunnel.network.fake.FakeNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class UserViewModel(
    private val userDao: UserDao,
    private val assetManager: AssetManager
) : ViewModel() {

    private var userRepository: UserRepository
    lateinit var usersLiveData: LiveData<List<User>>
    private val ioDispatcher = Dispatchers.IO

    init {
        val fakeNetworkDataSource = FakeNetworkDataSource(ioDispatcher, Json, assetManager)
        userRepository = FakeNetworkUserRepository(userDao, ioDispatcher, fakeNetworkDataSource)
    }

    fun onViewReady() {
        fetchAllUsers()
    }

    private fun fetchAllUsers() {
        viewModelScope.launch(ioDispatcher) {
            userRepository.syncInDatabase()
//            usersLiveData = userRepository.getUsers().asLiveData()

//            檢查是否有從dataSource，取得使用者資料
            userRepository.getUsers().collect { userList ->
                userList.map { user ->
                    Log.d("userRepository", user.toString())
                }
            }
//            檢查資料庫中，是否有使用者資料
            userDao.getUserEntities().collect { userEntityList ->
                userEntityList.map { userEntity ->
                    Log.d("userEntity", userEntity.toString())
                }
            }
        }
    }
}