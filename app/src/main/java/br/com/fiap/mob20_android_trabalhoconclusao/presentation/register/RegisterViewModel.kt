package br.com.fiap.mob20_android_trabalhoconclusao.presentation.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.Item
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.NewItem
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetItemUseCase
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.SaveItemUseCase
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.UpdateItemUseCase
import kotlinx.coroutines.launch

class RegisterViewModel(
        private val saveItemUseCase: SaveItemUseCase,
        private val getItemUseCase: GetItemUseCase,
        private val updateItemUseCase: UpdateItemUseCase
): ViewModel() {

    var itemSaveState = MutableLiveData<RequestState<NewItem>>()
    var getItemState =  MutableLiveData<RequestState<Item>>()
    var itemUpdateState = MutableLiveData<RequestState<String>>()

    fun saveItem(name: String, location: String, phone: String, description: String, zipCode: String) {
        itemSaveState.value = RequestState.Loading
        val item = NewItem(
                name,
                location,
                phone,
                description,
                zipCode,
                ""
        )
        viewModelScope.launch {
            itemSaveState.value = saveItemUseCase.save(item)
        }
    }

     fun getItem(id: String){
         viewModelScope.launch {
             getItemState.value = getItemUseCase.getItem(id)
         }
    }

    fun updateItem(name: String, location: String, phone: String ,description: String, zipCode: String, itemId: String){
        val item = Item(
                name,
                location,
                phone,
                description,
                zipCode,
                itemId
        )
        viewModelScope.launch {
            itemUpdateState.value = updateItemUseCase.update(item)
        }
    }
}
