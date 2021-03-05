package br.com.fiap.mob20_android_trabalhoconclusao.domain.repository

import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.NewUser
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.User
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.UserLogin

interface UserRepository {
    suspend fun getUserLogged(): RequestState<User>

    suspend fun  doLogin(userLogin: UserLogin): RequestState<User>

    suspend fun resendPassword(email: String): RequestState<String>

    suspend fun create(newUser: NewUser): RequestState<User>

    suspend fun logout(): RequestState<Boolean>
}