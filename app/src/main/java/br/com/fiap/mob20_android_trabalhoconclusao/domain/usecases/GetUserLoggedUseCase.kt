package br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases

import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.User
import br.com.fiap.mob20_android_trabalhoconclusao.domain.repository.UserRepository

class GetUserLoggedUseCase(
    private val userRepository: UserRepository
) {
    suspend fun getUserLogged(): RequestState<User> =
        userRepository.getUserLogged()
}