package br.com.fiap.mob20_android_trabalhoconclusao.presentation.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.Item
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetItemsUseCase
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.SaveItemUseCase
import kotlinx.coroutines.launch

class RegisterViewModel(
        private val saveItemUseCase: SaveItemUseCase,
        private val getItemsUseCase: GetItemsUseCase
): ViewModel() {

    var itemSaveState = MutableLiveData<RequestState<Item>>()
    val itemsSelectedState = MutableLiveData<RequestState<List<Item>>>()

    fun saveItem(name: String, location: String, phone: String ,description: String ) {
        itemSaveState.value = RequestState.Loading
        val item = Item(
                name,
                location,
                phone,
                description,
                ""
        )
        viewModelScope.launch {
            itemSaveState.value = saveItemUseCase.save(item)
        }
    }

    fun getItems() {
        viewModelScope.launch {
            itemsSelectedState.value = getItemsUseCase.getList()
        }
    }
}
