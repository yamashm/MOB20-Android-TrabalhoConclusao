package br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases

import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.Item
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.repository.ItemRepository

class SaveItemUseCase (
        private val getUserLoggedUseCase : GetUserLoggedUseCase,
        private val itemRepository : ItemRepository
) {
    suspend fun save(item: Item): RequestState<Item> {
        val userLogged = getUserLoggedUseCase.getUserLogged()
        return when (userLogged ) {
            is RequestState .Success -> {
                item.userId = userLogged .data.id
                itemRepository.save(item)
            }
            is RequestState.Loading -> {
                RequestState.Loading
            }
            is RequestState.Error -> {
                RequestState.Error(Exception( "Usuário não encontrado para associar o item" ))
            }
        }
    }
}