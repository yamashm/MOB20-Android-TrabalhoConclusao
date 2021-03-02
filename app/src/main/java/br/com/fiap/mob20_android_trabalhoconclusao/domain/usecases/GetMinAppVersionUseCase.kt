package br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases

import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.repository.AppRepository

class GetMinAppVersionUseCase(
        private val appRespository: AppRepository
) {
    suspend fun getMinVersionApp(): RequestState<Int> =
            appRespository.getMinVersionApp()
}