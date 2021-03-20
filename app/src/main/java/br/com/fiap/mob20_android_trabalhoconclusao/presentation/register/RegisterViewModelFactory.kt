package br.com.fiap.mob20_android_trabalhoconclusao.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetItemUseCase
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.SaveItemUseCase
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.UpdateItemUseCase

class RegisterViewModelFactory (
        private val saveItemUseCase: SaveItemUseCase,
        private val getItemUseCase: GetItemUseCase,
        private val updateItemUseCase: UpdateItemUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(SaveItemUseCase::class.java, GetItemUseCase::class.java, UpdateItemUseCase::class.java)
                .newInstance(saveItemUseCase, getItemUseCase, updateItemUseCase)
    }
}