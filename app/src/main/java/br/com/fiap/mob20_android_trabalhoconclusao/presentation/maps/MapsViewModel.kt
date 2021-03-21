package br.com.fiap.mob20_android_trabalhoconclusao.presentation.maps

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.Item
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.User
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetItemsUseCase
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetUserLoggedUseCase
import kotlinx.coroutines.launch

class MapsViewModel (private val getItemsUseCase: GetItemsUseCase
): ViewModel() {
    val getItemsState = MutableLiveData<RequestState<List<Item>>>()

    fun getItems(){
        getItemsState.value = RequestState.Loading
        viewModelScope.launch {
            getItemsState.value = getItemsUseCase.getList()
        }
    }
}