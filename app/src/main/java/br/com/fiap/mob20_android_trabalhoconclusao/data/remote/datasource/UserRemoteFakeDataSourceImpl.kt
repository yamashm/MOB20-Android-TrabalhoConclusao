package br.com.fiap.mob20_android_trabalhoconclusao.data.remote.datasource

import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay

@ExperimentalCoroutinesApi
class UserRemoteFakeDataSourceImpl : UserRemoteDataSource {
    override suspend fun getUserLogged(): RequestState<User> {
        delay(2000)
        return RequestState.Success(User("Marcelo"))
    }
}