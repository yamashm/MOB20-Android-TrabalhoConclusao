package br.com.fiap.mob20_android_trabalhoconclusao.data.repository

import br.com.fiap.mob20_android_trabalhoconclusao.data.remote.datasource.ItemRemoteDataSource
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.Item
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.repository.ItemRepository

class ItemRepositoryImpl(
        private val itemRemoteDataSource: ItemRemoteDataSource
): ItemRepository {

    override suspend fun save(item: Item): RequestState<Item> {
        return itemRemoteDataSource.save(item)
    }

    override suspend fun getList(): RequestState<List<Item>> {
        return itemRemoteDataSource.getList()
    }

    override suspend fun delete(id: String): RequestState<String> {
        return itemRemoteDataSource.delete(id)
    }
}