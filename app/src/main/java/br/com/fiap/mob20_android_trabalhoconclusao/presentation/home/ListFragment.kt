package br.com.fiap.mob20_android_trabalhoconclusao.presentation.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.mob20_android_trabalhoconclusao.R
import br.com.fiap.mob20_android_trabalhoconclusao.data.remote.datasource.ItemRemoteFirebaseDataSourceImpl
import br.com.fiap.mob20_android_trabalhoconclusao.data.remote.datasource.UserRemoteFirebaseDataSourceImpl
import br.com.fiap.mob20_android_trabalhoconclusao.data.repository.ItemRepositoryImpl
import br.com.fiap.mob20_android_trabalhoconclusao.data.repository.UserRepositoryImpl
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.Item
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.ListItem
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetItemsUseCase
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetUserLoggedUseCase
import br.com.fiap.mob20_android_trabalhoconclusao.presentation.base.auth.BaseAuthFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.ArrayList


class ListFragment : BaseAuthFragment() {
    override val layout = R.layout.fragment_list

    private lateinit var rvHomeList: RecyclerView


    private val listViewModel: ListViewModel by lazy{
        ViewModelProvider(
                this,
                ListViewModelFactory(
                        GetItemsUseCase(
                                ItemRepositoryImpl(
                                    ItemRemoteFirebaseDataSourceImpl(
                                        FirebaseAuth.getInstance(),
                                        FirebaseFirestore.getInstance()
                                )
                        )
                ) ,
                    GetUserLoggedUseCase(
                        UserRepositoryImpl(
                            UserRemoteFirebaseDataSourceImpl(
                                FirebaseAuth.getInstance(),
                                FirebaseFirestore.getInstance()
                            )
                        )
                    )
                )
        ).get(ListViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView(view)

        registerObserver()

        listViewModel.getItems()

    }

    private fun setUpView(view: View) {
        rvHomeList = view.findViewById(R.id.rvHomeList)

    }

    private fun setUpList(items: List<ListItem>) {
        rvHomeList.adapter = HomeAdapter(items, this::clickItem)
    }

    private fun clickItem(item: ListItem) {

    }

    private fun setUpListener() {

    }

    private fun registerObserver() {
        listViewModel.getItemsState.observe(viewLifecycleOwner, Observer {
            when(it) {
                is RequestState.Loading -> {
                    showLoading()
                }
                is RequestState.Success -> {
                    hideLoading()

                    val listItems:  MutableList<ListItem> = ArrayList()

                    for ((name, location, phone, description, userId) in it.data) {
                        var item: ListItem = ListItem(name, location, phone)

                        listItems.add(item)
                    }

                    setUpList(listItems)
                }
            }
        })
    }
}