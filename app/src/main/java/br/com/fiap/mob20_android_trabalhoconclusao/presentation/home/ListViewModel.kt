package br.com.fiap.mob20_android_trabalhoconclusao.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.Item
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetItemsUseCase
import kotlinx.coroutines.launch

class ListViewModel(
        private val getItemsUseCase: GetItemsUseCase
): ViewModel() {

    val getItemsState = MutableLiveData<RequestState<List<Item>>>()

    fun getItems(userId: String){
        viewModelScope.launch {
            getItemsState.value = getItemsUseCase.getList(userId)
        }
    }
}