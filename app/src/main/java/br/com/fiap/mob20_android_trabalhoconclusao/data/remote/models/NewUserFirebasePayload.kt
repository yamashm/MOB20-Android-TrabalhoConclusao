package br.com.fiap.mob20_android_trabalhoconclusao.data.remote.models

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class NewUserFirebasePayload(
        val name: String? = null,
        val email: String? = null,
        val phone: String? = null,
        @get: Exclude val password: String? = null
)