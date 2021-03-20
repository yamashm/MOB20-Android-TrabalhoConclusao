package br.com.fiap.mob20_android_trabalhoconclusao.presentation.home

import android.opengl.GLES10
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.Item
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.User
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.DeleteItemUseCase
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetItemsUseCase
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetUserLoggedUseCase
import kotlinx.coroutines.launch

class ListViewModel(
        private val getItemsUseCase: GetItemsUseCase,
        private val getUserLoggedUseCase: GetUserLoggedUseCase,
        private val deleteItemUseCase: DeleteItemUseCase
): ViewModel() {

    val getItemsState = MutableLiveData<RequestState<List<Item>>>()
    val deleteItemState = MutableLiveData<RequestState<String>>()
    val getUserState = MutableLiveData<RequestState<String>>()
    var userLogged: User? = null

    fun getUser(){
        viewModelScope.launch {
            val userReponse = getUserLoggedUseCase.getUserLogged()

            setUpUser(userReponse)
        }
    }

    private fun setUpUser(userResponse: RequestState<User>) {
        if(userResponse is RequestState.Success){
            userLogged = userResponse.data
            getUserState.value = RequestState.Success(userResponse.data.name)
        } else
        {
            userLogged = null
        }
    }

    fun getItems(){
        getItemsState.value = RequestState.Loading
        viewModelScope.launch {
            getItemsState.value = getItemsUseCase.getList()
        }
    }

    fun deleteItem(id: String){
        deleteItemState.value = RequestState.Loading
        viewModelScope.launch {
            deleteItemState.value = deleteItemUseCase.delete(id)
        }
    }
}