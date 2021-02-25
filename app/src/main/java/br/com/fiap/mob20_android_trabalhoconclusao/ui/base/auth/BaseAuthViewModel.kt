package br.com.fiap.mob20_android_trabalhoconclusao.ui.base.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.User
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetUserLoggedUseCase
import kotlinx.coroutines.launch

class BaseAuthViewModel(
    private val getUserLoggedUseCase: GetUserLoggedUseCase
) : ViewModel() {
    var userLogged = MutableLiveData<RequestState<User>>()

    fun getUserLogged(){
        viewModelScope.launch {
            userLogged.value = getUserLoggedUseCase.getUserLogged()
        }
    }
}