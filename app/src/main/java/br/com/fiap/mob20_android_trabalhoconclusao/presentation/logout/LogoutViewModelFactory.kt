package br.com.fiap.mob20_android_trabalhoconclusao.presentation.logout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.LogoutUserCase

class  LogoutViewModelFactory(
        private val logout: LogoutUserCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(LogoutUserCase::class.java).newInstance(logout)
    }

}