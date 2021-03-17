package br.com.fiap.mob20_android_trabalhoconclusao.data.remote.mapper

import br.com.fiap.mob20_android_trabalhoconclusao.data.remote.models.GetItemFirebase
import br.com.fiap.mob20_android_trabalhoconclusao.data.remote.models.NewItemFirebasePayload
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.Item
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.NewItem

object NewItemFirebasePayloadMapper {
    fun mapToItem(getItemFirebaseMapper: GetItemFirebase) = Item(
            name = getItemFirebaseMapper.name ?: "",
            location = getItemFirebaseMapper.location ?: "",
            description = getItemFirebaseMapper.description ?: ""
    )
    fun mapToNewItemFirebasePayload (newItem: NewItem) = NewItemFirebasePayload(
            name = newItem.name,
            location = newItem.location,
            description = newItem.description
    )
}