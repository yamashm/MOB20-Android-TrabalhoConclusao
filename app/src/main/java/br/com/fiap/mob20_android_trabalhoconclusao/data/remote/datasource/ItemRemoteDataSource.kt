package br.com.fiap.mob20_android_trabalhoconclusao.data.remote.datasource

import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.Item
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState

interface ItemRemoteDataSource{
    suspend fun getList(): RequestState<List<Item>>

    suspend fun save(car: Item): RequestState<Item>
}