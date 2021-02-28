package br.com.fiap.mob20_android_trabalhoconclusao.data.repository

import br.com.fiap.mob20_android_trabalhoconclusao.data.remote.datasource.UserRemoteDataSource
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.NewUser
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.User
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.UserLogin
import br.com.fiap.mob20_android_trabalhoconclusao.domain.repository.UserRepository

data class UserRepositoryImpl(
    val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {
    override suspend fun getUserLogged(): RequestState<User> {
        return userRemoteDataSource.getUserLogged()
    }

    override suspend fun doLogin(userLogin: UserLogin): RequestState<User> {
        return userRemoteDataSource.doLogin(userLogin)
    }

    override suspend fun resendPassword(email: String):
            RequestState<String> {
        return userRemoteDataSource.resendPassword(email)
    }

    override suspend fun create(newUser: NewUser): RequestState<User> {
        return userRemoteDataSource.create(newUser)
    }
}