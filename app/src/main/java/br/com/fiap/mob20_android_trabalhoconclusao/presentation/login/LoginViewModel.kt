package br.com.fiap.mob20_android_trabalhoconclusao.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.User
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.UserLogin
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.LoginUseCase
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.ResendPasswordUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val resendPasswordUseCase: ResendPasswordUseCase
): ViewModel() {

    val loginState = MutableLiveData<RequestState<User>>()
    val resendPasswordState = MutableLiveData<RequestState<String>>()

    fun doLogin(email: String, password: String) {
        loginState.value = RequestState.Loading
        viewModelScope.launch {
            loginState.value = loginUseCase.doLogin(UserLogin(email, password))
        }
    }

    fun resendPassword(email: String) {
        viewModelScope.launch {
            resendPasswordState.value = resendPasswordUseCase.resendPassword(email)
        }
    }
}
