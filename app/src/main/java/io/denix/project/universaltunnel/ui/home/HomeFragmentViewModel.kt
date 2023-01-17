package io.denix.project.universaltunnel.ui.home

import android.app.Application
import android.content.res.AssetManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.denix.project.universaltunnel.data.user.model.UserDao
import io.denix.project.universaltunnel.data.user.repository.FakeNetworkUserRepository
import io.denix.project.universaltunnel.data.user.repository.UserRepository
import io.denix.project.universaltunnel.network.fake.FakeNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class HomeFragmentViewModel(
    application: Application,
    private val userId: Int,
    private val userDao: UserDao,
    private val assetManager: AssetManager
) : AndroidViewModel(application) {

    private var userRepository: UserRepository
    private val ioDispatcher = Dispatchers.IO

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    init {
        val fakeNetworkDataSource = FakeNetworkDataSource(ioDispatcher, Json, assetManager)
        userRepository = FakeNetworkUserRepository(userDao, ioDispatcher, fakeNetworkDataSource)

        viewModelScope.launch(ioDispatcher) {
            userRepository.getUser(userId).collect {
                val firstName = it.firstName
                val lastName = it.lastName

                _text.postValue("Hi, $firstName $lastName \n Welcome to UT")
            }
        }
    }
}