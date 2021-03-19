package br.com.fiap.mob20_android_trabalhoconclusao.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetItemsUseCase
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetUserLoggedUseCase

class ListViewModelFactory (
    private val getItemsUseCase: GetItemsUseCase,
    private val getUserLoggedUseCase: GetUserLoggedUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(GetItemsUseCase::class.java, GetUserLoggedUseCase::class.java
        ).newInstance(getItemsUseCase, getUserLoggedUseCase)
    }
}