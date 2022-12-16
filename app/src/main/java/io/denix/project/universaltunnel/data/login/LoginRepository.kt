package io.denix.project.universaltunnel.data.login

class LoginRepository(
    private val loginDao: LoginDao,
) {
    fun getAllLoginData(): List<LoginEntity> {
        return loginDao.getLoginEntities()
    }

    fun getUserLoginData(userId: Int): List<LoginEntity> {
        return loginDao.getLoginEntities(userId)
    }

    suspend fun login(userId: Int) {
        val loginEntity = LoginEntity(null, userId, true, System.currentTimeMillis())
        loginDao.insertLogin(loginEntity)
    }

    suspend fun logout(userId: Int) {
        val loginEntity = LoginEntity(null, userId, false, System.currentTimeMillis())
        loginDao.insertLogin(loginEntity)
    }

    suspend fun cleanUpLoginData() {
        loginDao.deleteAllLogins()
    }
}