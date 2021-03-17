package br.com.fiap.mob20_android_trabalhoconclusao.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.User
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetUserLoggedUseCase
import kotlinx.coroutines.launch

class HomeViewModel (
        private val getUserLoggedUseCase: GetUserLoggedUseCase
): ViewModel(){

    var userLogged: User? = null

    fun getUser(){
        viewModelScope.launch {
            val userReponse = getUserLoggedUseCase.getUserLogged()
        }
    }

    private fun setUpUser(userResponse: RequestState<User>) {
        when(userResponse) {
            is RequestState.Success -> userLogged = userResponse.data
            else -> userLogged = null
        }
    }
}