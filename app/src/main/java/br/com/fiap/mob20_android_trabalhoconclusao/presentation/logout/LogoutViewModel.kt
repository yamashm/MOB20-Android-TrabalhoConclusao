package br.com.fiap.mob20_android_trabalhoconclusao.presentation.logout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.LogoutUserCase
import kotlinx.coroutines.launch

class LogoutViewModel (
        private  val logoutUserCase: LogoutUserCase
): ViewModel() {

    val logout = MutableLiveData<RequestState<Boolean>>()

    fun logout() {
        viewModelScope.launch {

            logout.value = logoutUserCase.logout()
        }
    }
}
