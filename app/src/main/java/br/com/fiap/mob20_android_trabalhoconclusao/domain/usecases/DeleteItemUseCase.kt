package br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases

import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.repository.ItemRepository

class DeleteItemUseCase ( private val itemRepository: ItemRepository){
    suspend fun delete(id: String): RequestState<String>{
        return itemRepository.delete(id)
    }
}