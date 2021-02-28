package br.com.fiap.mob20_android_trabalhoconclusao.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.CreateUserUseCase

class SignUpViewModelFactory(
    private val createUserUseCase: CreateUserUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CreateUserUseCase::class.java)
            .newInstance(createUserUseCase)
    }
}