package br.com.fiap.mob20_android_trabalhoconclusao.presentation.integration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.Item
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetItemUseCase
import kotlinx.coroutines.launch

class IntegrationViewModel( private val getItemUseCase: GetItemUseCase): ViewModel()
{
    var getItemState =  MutableLiveData<RequestState<Item>>()

    fun getItem(id: String){
        viewModelScope.launch {
            getItemState.value = getItemUseCase.getItem(id)
        }
    }
}