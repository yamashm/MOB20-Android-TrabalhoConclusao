package br.com.fiap.mob20_android_trabalhoconclusao.data.remote.datasource

import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.Item
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import java.util.*


class ItemRemoteFirebaseDataSourceImpl(
    private val mAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
): ItemRemoteDataSource {

    override suspend fun getList(): RequestState<List<Item>> {
         return try{
             mAuth.currentUser?.reload()
             val firebaseUser = mAuth.currentUser

             if (firebaseUser != null) {
                 val list: MutableList<Item> = ArrayList()

                 firebaseFirestore.collection("Items")
                     .whereEqualTo("userId", firebaseUser.uid)
                     .get().addOnCompleteListener(object : OnCompleteListener<QuerySnapshot?> {
                         override fun onComplete(task: Task<QuerySnapshot?>) {
                             if (task.isSuccessful()) {

                                 for (document in task.getResult()!!) {
                                     val taskItem: Item = document.toObject(Item::class.java)
                                     list.add(taskItem)
                                 }
                             }
                         }
                     }).await()

                 RequestState.Success(list)
             } else
             {
                 RequestState.Error(Exception("Usuário não logado"))
             }

        } catch (e: Exception){
            RequestState.Error(e)
        }
    }

    override suspend fun save(item: Item): RequestState<Item> {
        return try {
            firebaseFirestore.collection("Items")
                    .add(item)
                    .await()
            RequestState.Success(item)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

}