package br.com.fiap.mob20_android_trabalhoconclusao.data.remote.datasource

import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.Item
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.NewItem
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.exceptions.ItemNotFoundException
import br.com.fiap.mob20_android_trabalhoconclusao.domain.exceptions.UserNotLoggedException
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
                                     taskItem.itemId = document.id
                                     list.add(taskItem)
                                 }
                             }
                             else{
                                 RequestState.Error(UserNotLoggedException())
                             }
                         }
                     }).await()

                 RequestState.Success(list)
             } else
             {
                 RequestState.Error(UserNotLoggedException())
             }

        } catch (e: Exception){
            RequestState.Error(e)
        }
    }

    override suspend fun save(item: NewItem): RequestState<NewItem> {
        return try {
            firebaseFirestore.collection("Items")
                    .add(item)
                    .await()

            RequestState.Success(item)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

    override  suspend fun delete(id: String): RequestState<String>{
        return try{
            firebaseFirestore.collection("Items").document(id)
                    .delete()
                    .await()

            RequestState.Success("")
        } catch (e: Exception){
            RequestState.Error(e)
        }
    }

    override suspend fun getItem(id: String): RequestState<Item> {
        return try{
            val item = firebaseFirestore.collection("Items").document(id)
                    .get()
                    .await()
                    .toObject(Item::class.java)

            if(item == null)
                RequestState.Error(ItemNotFoundException())
            else
                RequestState.Success(item)
        } catch (e: Exception){
            RequestState.Error(e)
        }
    }

    override suspend fun update(item: Item): RequestState<String> {
        return try{
            firebaseFirestore.collection("Items").document(item.itemId)
                    .update("name", item.name,
                            "location", item.location,
                    "phone", item.phone,
                    "description", item.description, "zipCode", item.zipCode).await()


            RequestState.Success("")
        } catch (e: Exception){
            RequestState.Error(e)
        }
    }



}