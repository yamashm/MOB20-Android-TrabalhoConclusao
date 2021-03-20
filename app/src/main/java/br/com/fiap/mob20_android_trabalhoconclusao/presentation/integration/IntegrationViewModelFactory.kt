package br.com.fiap.mob20_android_trabalhoconclusao.presentation.integration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetItemUseCase

class IntegrationViewModelFactory(private val getItemUseCase: GetItemUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor( GetItemUseCase::class.java)
            .newInstance( getItemUseCase)
    }
}