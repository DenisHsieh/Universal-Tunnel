package io.denix.project.universaltunnel.data.user.repository

import io.denix.project.universaltunnel.data.external.User
import io.denix.project.universaltunnel.network.fake.FakeNetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class FakeNetworkUserRepository(
    private val ioDispatcher: CoroutineDispatcher,
    private val dataSource: FakeNetworkDataSource
) : UserRepository {
    override fun getUsers(): Flow<List<User>> = flow {
        emit(
            dataSource.getUsers().map {
                User(
                    id = it.id,
                    firstName = it.firstName,
                    lastName = it.lastName,
                    age= it.age,
                    gender = it.gender,
                    weight = it.weight,
                    height = it.height
                )
            }
        )
    }.flowOn(ioDispatcher)

    override fun getUser(id: Int): Flow<User> {
        return getUsers().map { it.first { user -> user.id == id } }
    }

    override suspend fun sync() {}
}