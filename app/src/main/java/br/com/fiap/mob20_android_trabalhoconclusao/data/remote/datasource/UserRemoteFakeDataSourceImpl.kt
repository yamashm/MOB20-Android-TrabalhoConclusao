package br.com.fiap.mob20_android_trabalhoconclusao.data.remote.datasource

import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.NewUser
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.User
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.UserLogin
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay

@ExperimentalCoroutinesApi
class UserRemoteFakeDataSourceImpl : UserRemoteDataSource {
    override suspend fun getUserLogged(): RequestState<User> {
        delay(2000)
        return RequestState.Success(User("Marcelo"))
    }

    override suspend fun doLogin(userLogin: UserLogin): RequestState<User> {
        TODO("Not yet implemented")
    }

    override suspend fun resendPassword(email: String): RequestState<String> {
        TODO("Not yet implemented")
    }

    override suspend fun create(newUser: NewUser): RequestState<User> {
        TODO("Not yet implemented")
    }

    override suspend fun logout(): RequestState<Boolean> {
        TODO("Not yet implemented")
    }
}