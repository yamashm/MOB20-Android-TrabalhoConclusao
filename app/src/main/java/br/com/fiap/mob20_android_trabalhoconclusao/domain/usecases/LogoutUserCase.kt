package br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases

import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.repository.UserRepository
class LogoutUserCase(
        private val userRepository: UserRepository
){
    suspend fun logout(): RequestState<Boolean> {
        return userRepository.logout()
    }
}