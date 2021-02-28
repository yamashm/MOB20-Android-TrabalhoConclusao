package br.com.fiap.mob20_android_trabalhoconclusao.presentation.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.NewUser
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.User
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.CreateUserUseCase
import kotlinx.coroutines.launch

class SignUpViewModel (
    private val createUserUseCase : CreateUserUseCase
) : ViewModel() {
    val newUserState = MutableLiveData<RequestState<User>>()
    fun create(name: String, email: String, phone: String, password: String) {
        viewModelScope.launch {
            newUserState .value = createUserUseCase .create(
                NewUser(
                    name,
                    email,
                    phone,
                    password
                )
            )
        }
    }
}