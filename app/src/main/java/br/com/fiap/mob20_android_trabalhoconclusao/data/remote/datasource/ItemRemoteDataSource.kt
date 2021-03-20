package br.com.fiap.mob20_android_trabalhoconclusao.data.remote.datasource

import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.Item
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.ListItem
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.NewItem
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState

interface ItemRemoteDataSource{
    suspend fun getList(): RequestState<List<Item>>

    suspend fun save(item: NewItem): RequestState<NewItem>

    suspend fun delete(id: String): RequestState<String>

    suspend fun getItem(id: String): RequestState<Item>

    suspend fun update(item: Item): RequestState<String>

}