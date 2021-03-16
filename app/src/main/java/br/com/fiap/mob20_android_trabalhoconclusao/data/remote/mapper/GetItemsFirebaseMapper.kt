package br.com.fiap.mob20_android_trabalhoconclusao.data.remote.mapper

import br.com.fiap.mob20_android_trabalhoconclusao.data.remote.models.GetItemFirebase
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.Item

fun mapToItem(getItemFirebaseMapper: GetItemFirebase) = Item(
        name = getItemFirebaseMapper.name ?: "",
        location = getItemFirebaseMapper.location ?: "",
        description = getItemFirebaseMapper.description?: ""
)