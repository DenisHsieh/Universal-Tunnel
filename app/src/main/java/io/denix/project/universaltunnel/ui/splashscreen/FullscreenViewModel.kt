package io.denix.project.universaltunnel.ui.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.denix.project.universaltunnel.data.login.LoginDao
import io.denix.project.universaltunnel.data.login.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class FullscreenViewModel(
    private val loginDao: LoginDao
) : ViewModel() {

    private val loginRepository: LoginRepository = LoginRepository(loginDao)
    private val ioDispatcher = Dispatchers.IO

    suspend fun checkLoginStatus(): Boolean {
        val isLogin = viewModelScope.async(ioDispatcher) {
            val loginDataList = loginRepository.getAllLoginData()
            return@async loginDataList.isNotEmpty()
        }
        return isLogin.await()
    }
}