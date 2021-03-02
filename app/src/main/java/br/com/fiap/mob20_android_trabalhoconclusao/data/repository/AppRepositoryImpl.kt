package br.com.fiap.mob20_android_trabalhoconclusao.data.repository

import br.com.fiap.mob20_android_trabalhoconclusao.data.remote.datasource.AppRemoteDataSource
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.repository.AppRepository

class AppRepositoryImpl(
        private val appRemoteDataSource: AppRemoteDataSource
) : AppRepository {
    override suspend fun getMinVersionApp(): RequestState<Int>
    {
        return appRemoteDataSource.getMinVersionApp()
    }

}