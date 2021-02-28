package br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases

import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.repository.UserRepository

class ResendPasswordUseCase(
    private val userRepository: UserRepository
) {
    suspend fun resendPassword(email: String): RequestState<String> =
        userRepository.resendPassword(email)
}