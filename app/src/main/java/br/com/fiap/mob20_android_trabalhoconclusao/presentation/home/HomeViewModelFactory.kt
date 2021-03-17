package br.com.fiap.mob20_android_trabalhoconclusao.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetUserLoggedUseCase

class HomeViewModelFactory (
        private val getUserLoggedUseCase: GetUserLoggedUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor( GetUserLoggedUseCase::class.java)
                .newInstance( getUserLoggedUseCase)
    }
}