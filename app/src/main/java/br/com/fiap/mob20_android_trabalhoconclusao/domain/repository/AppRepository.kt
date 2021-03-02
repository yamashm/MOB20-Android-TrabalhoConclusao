package br.com.fiap.mob20_android_trabalhoconclusao.domain.repository

import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState

interface AppRepository {
    suspend fun getMinVersionApp(): RequestState<Int>

}