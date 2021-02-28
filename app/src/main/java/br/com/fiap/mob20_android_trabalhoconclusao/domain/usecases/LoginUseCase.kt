package br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases

import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.User
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.UserLogin
import br.com.fiap.mob20_android_trabalhoconclusao.domain.exceptions.EmailBlankException
import br.com.fiap.mob20_android_trabalhoconclusao.domain.exceptions.PasswordBlankException
import br.com.fiap.mob20_android_trabalhoconclusao.domain.repository.UserRepository

class LoginUseCase(
    private val userRepository: UserRepository
){
    suspend fun doLogin(userLogin: UserLogin):RequestState<User> {
        if(userLogin.email.isBlank()){
            RequestState.Error(EmailBlankException())
        }

        if(userLogin.password.isBlank()){
            RequestState.Error(PasswordBlankException())
        }
        return userRepository.doLogin(userLogin)

    }
}