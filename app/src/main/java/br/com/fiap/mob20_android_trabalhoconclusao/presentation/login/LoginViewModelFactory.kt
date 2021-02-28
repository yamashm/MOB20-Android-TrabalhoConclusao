package br.com.fiap.mob20_android_trabalhoconclusao.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.LoginUseCase
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.ResendPasswordUseCase

class LoginViewModelFactory(
    private val loginUseCase: LoginUseCase,
    private val resendPasswordUseCase: ResendPasswordUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(LoginUseCase::class.java, ResendPasswordUseCase::class.java).newInstance(loginUseCase, resendPasswordUseCase)
    }
}