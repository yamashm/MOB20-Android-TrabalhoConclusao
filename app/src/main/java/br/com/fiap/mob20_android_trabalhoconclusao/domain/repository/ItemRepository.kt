package br.com.fiap.mob20_android_trabalhoconclusao.domain.repository

import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.Item
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState

interface ItemRepository {
    suspend fun save(item: Item): RequestState<Item>

    suspend fun getList(): RequestState<List<Item>>

    suspend fun delete(id: String): RequestState<String>
}