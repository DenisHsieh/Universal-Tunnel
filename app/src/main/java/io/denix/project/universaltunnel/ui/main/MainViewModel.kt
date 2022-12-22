package io.denix.project.universaltunnel.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.denix.project.universaltunnel.data.external.User
import io.denix.project.universaltunnel.data.login.LoginDao
import io.denix.project.universaltunnel.data.login.LoginRepository
import io.denix.project.universaltunnel.data.user.model.UserDao
import io.denix.project.universaltunnel.data.user.model.asExternalModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel(
    private val loginDao: LoginDao,
    private val userDao: UserDao
) : ViewModel() {

    private val loginRepository: LoginRepository = LoginRepository(loginDao)
    private val ioDispatcher = Dispatchers.IO

    suspend fun getUserData(userId: Int): User {
        val user = viewModelScope.async(ioDispatcher) {
            val userEntity = userDao.getUserEntity(userId)
            return@async userEntity.asExternalModel()
        }
        return user.await()
    }

    suspend fun getUserFromLoginData(): User {
        val user = viewModelScope.async(ioDispatcher) {
            val loginEntity = loginRepository.getAllLoginData().last().also { it.loginStatus }
            val userEntity = loginEntity.userId!!.let { userDao.getUserEntity(it) }
            return@async userEntity.asExternalModel()
        }
        return user.await()
    }

    fun logout(userId: Int){
        viewModelScope.launch(ioDispatcher) {
            loginRepository.logout(userId)
        }
    }

    fun cleanUpLoginData() {
        viewModelScope.launch(ioDispatcher) {
            loginRepository.cleanUpLoginData()
        }
    }
}