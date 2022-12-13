package io.denix.project.universaltunnel.data.user.repository

import io.denix.project.universaltunnel.data.external.User
import io.denix.project.universaltunnel.data.user.model.UserDao
import io.denix.project.universaltunnel.data.user.model.UserEntity
import io.denix.project.universaltunnel.data.user.model.asEntity
import io.denix.project.universaltunnel.data.user.model.asExternalModel
import io.denix.project.universaltunnel.network.NetworkDataSource
import io.denix.project.universaltunnel.network.model.NetworkUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflineUserRepository(
    private val userDao: UserDao,
    private val network: NetworkDataSource
) : UserRepository {

    override fun getUsers(): Flow<List<User>> {
        return userDao.getUserEntities().map {
            it.map(UserEntity::asExternalModel)
        }
    }

    override fun getUser(id: Int): Flow<User> {
        return userDao.getUserEntity(id).map {
            it.asExternalModel()
        }
    }

    override suspend fun sync() {
        val networkUsers = network.getUsers()
        userDao.upsertUsers(
            entities = networkUsers.map(NetworkUser::asEntity)
        )
    }
}