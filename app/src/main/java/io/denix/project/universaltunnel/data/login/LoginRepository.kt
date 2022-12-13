package io.denix.project.universaltunnel.data.login

import androidx.annotation.WorkerThread

class LoginRepository(private val loginDao: LoginDao) {
    val loginData: Login = loginDao.getLoginData()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun login(login: Login) {
        loginDao.insert(login)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun logout(login: Login) {
        loginDao.deleteAll()
    }
}