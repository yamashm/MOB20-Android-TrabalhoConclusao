package br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases

import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.Item
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.repository.ItemRepository

class UpdateItemUseCase ( private val itemRepository: ItemRepository){
    suspend fun update(item: Item): RequestState<String> {
        return itemRepository.update(item)
    }
}