package br.com.fiap.mob20_android_trabalhoconclusao.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.Item
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.User
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetItemsUseCase
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetUserLoggedUseCase
import kotlinx.coroutines.launch

class ListViewModel(
        private val getItemsUseCase: GetItemsUseCase,
        private val getUserLoggedUseCase: GetUserLoggedUseCase
): ViewModel() {

    val getItemsState = MutableLiveData<RequestState<List<Item>>>()
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

    fun getItems(){
        getItemsState.value = RequestState.Loading
        viewModelScope.launch {
            getItemsState.value = getItemsUseCase.getList()
        }
    }
}