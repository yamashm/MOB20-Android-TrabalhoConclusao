package br.com.fiap.mob20_android_trabalhoconclusao.data.remote.datasource

import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.extensions.fromRemoteConfig
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class AppRemoteFirebaseDataSourceImpl: AppRemoteDataSource {
    override suspend fun getMinVersionApp(): RequestState<Int> {
        val minVersion = Gson().fromRemoteConfig("RemoteConfigKeys.MIN_VERSION_APP", Int::class.java) ?: 0
        return RequestState.Success(minVersion)
    }
}