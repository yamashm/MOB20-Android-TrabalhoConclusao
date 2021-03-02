package br.com.fiap.mob20_android_trabalhoconclusao.data.remote.mapper

import br.com.fiap.mob20_android_trabalhoconclusao.data.remote.models.NewUserFirebasePayload
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.NewUser
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.User

object NewUserFirebasePayloadMapper {
    fun mapToNewUser (newUserFirebasePayload: NewUserFirebasePayload) = NewUser(
            name = newUserFirebasePayload. name ?: "",
            email = newUserFirebasePayload. email ?: "",
            phone = newUserFirebasePayload. phone ?: "",
            password = newUserFirebasePayload. password ?: ""
    )
    fun mapToNewUserFirebasePayload (newUser: NewUser) = NewUserFirebasePayload(
            name = newUser.name,
            email = newUser.email,
            phone = newUser.phone,
            password = newUser.password
    )
    fun mapToUser(newUserFirebasePayload: NewUserFirebasePayload ) = User(
            name = newUserFirebasePayload. name ?: ""
    )
}