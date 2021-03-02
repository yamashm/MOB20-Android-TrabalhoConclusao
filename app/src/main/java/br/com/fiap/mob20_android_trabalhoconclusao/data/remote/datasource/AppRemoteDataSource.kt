package br.com.fiap.mob20_android_trabalhoconclusao.data.remote.datasource

import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState

interface AppRemoteDataSource {
    suspend fun getMinVersionApp(): RequestState<Int>
}