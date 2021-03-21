package br.com.fiap.mob20_android_trabalhoconclusao.presentation.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetItemsUseCase
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetUserLoggedUseCase

class MapsViewModelFactory (private val getItemsUseCase: GetItemsUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(GetItemsUseCase::class.java
        ).newInstance(getItemsUseCase)
    }
}