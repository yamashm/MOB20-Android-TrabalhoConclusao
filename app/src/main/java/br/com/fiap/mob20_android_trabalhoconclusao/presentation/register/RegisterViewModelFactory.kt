package br.com.fiap.mob20_android_trabalhoconclusao.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetItemsUseCase
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.SaveItemUseCase

class RegisterViewModelFactory (
        private val saveItemUseCase: SaveItemUseCase,
        private val getItemsUseCase: GetItemsUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(SaveItemUseCase::class.java,
                GetItemsUseCase::class.java)

                .newInstance(saveItemUseCase,
                        getItemsUseCase)
    }
}