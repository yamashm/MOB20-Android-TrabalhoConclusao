package br.com.fiap.mob20_android_trabalhoconclusao.data.remote.datasource

import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.Item
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import com.google.firebase.firestore.FirebaseFirestore

class ItemRemoteFirebaseDataSourceImpl(
        private val firebaseFirestore: FirebaseFirestore
): ItemRemoteDataSource {

    override suspend fun getList(): RequestState<List<Item>> {
        return try{
            val items =  firebaseFirestore.collection("Items").get()

            RequestState.Error(Exception(""))

        } catch (e: Exception){
            RequestState.Error(e)
        }
    }

}