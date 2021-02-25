package br.com.fiap.mob20_android_trabalhoconclusao.domain.repository

import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.User

interface UserRepository {
    suspend fun getUserLogged(): RequestState<User>
}