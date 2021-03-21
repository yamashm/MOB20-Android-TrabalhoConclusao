package br.com.fiap.mob20_android_trabalhoconclusao.presentation.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.mob20_android_trabalhoconclusao.R
import br.com.fiap.mob20_android_trabalhoconclusao.data.remote.datasource.ItemRemoteFirebaseDataSourceImpl
import br.com.fiap.mob20_android_trabalhoconclusao.data.remote.datasource.UserRemoteFirebaseDataSourceImpl
import br.com.fiap.mob20_android_trabalhoconclusao.data.repository.ItemRepositoryImpl
import br.com.fiap.mob20_android_trabalhoconclusao.data.repository.UserRepositoryImpl
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.Item
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.ListItem
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.DeleteItemUseCase
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetItemsUseCase
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetUserLoggedUseCase
import br.com.fiap.mob20_android_trabalhoconclusao.presentation.base.auth.BaseAuthFragment
import br.com.fiap.mob20_android_trabalhoconclusaolib.alertdialog.CustomAlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.ArrayList


class ListFragment : BaseAuthFragment() {
    override val layout = R.layout.fragment_list

    private lateinit var rvHomeList: RecyclerView
    private lateinit var tvHomeHelloUser: TextView

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
                    ),
                        DeleteItemUseCase(
                                ItemRepositoryImpl(
                                        ItemRemoteFirebaseDataSourceImpl(
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

        listViewModel.getUser()
        listViewModel.getItems()

    }

    private fun setUpView(view: View) {
        rvHomeList = view.findViewById(R.id.rvHomeList)
        tvHomeHelloUser = view.findViewById(R.id.tvHomeHelloUser)

    }

    private fun setUpList(items: List<ListItem>) {
        rvHomeList.adapter = HomeAdapter(items, this::clickItem, this::clickDeleteItem, this::integrationClickListener)
    }

    private fun clickItem(item: ListItem) {
        var bundle = bundleOf("itemId" to item.itemId)
        findNavController().navigate(R.id.action_homeFragment_to_registerFragment, bundle);
    }

    private fun clickDeleteItem(id: String){

        val dialog = CustomAlertDialog()

        dialog.showDialog(
            requireActivity(),
            R.raw.alert8750,
            getString(R.string.dialog_delete),
            getString(R.string.dialog_delete_message),
            getString(R.string.dialog_ok),
            View.OnClickListener {
                dialog.dismissDialog()
                listViewModel.deleteItem(id)
            },
            getString(R.string.dialog_cancel),
            View.OnClickListener {
                dialog.dismissDialog()
            },
            false
        )
    }

    private fun integrationClickListener(id: String){
        var bundle = bundleOf("itemId" to id)
        findNavController().navigate(R.id.action_homeFragment_to_integrationFragment, bundle);
    }

    private fun registerObserver() {
        listViewModel.getItemsState.observe(viewLifecycleOwner, Observer {
            when(it) {
                is RequestState.Loading -> {
                    showLoading()
                }
                is RequestState.Success -> {

                    val listItems:  MutableList<ListItem> = ArrayList()

                    for ((name, location, phone, description, zipCode, itemId, userId) in it.data) {
                        var item: ListItem = ListItem(name, location, phone, description, zipCode, itemId)

                        listItems.add(item)
                    }

                    setUpList(listItems)
                    hideLoading()
                }
            }
        })

        listViewModel.deleteItemState.observe(viewLifecycleOwner, Observer {
            when(it) {
                is RequestState.Loading -> {
                    showLoading()
                }
                is RequestState.Success -> {
                    listViewModel.getItems()
                    hideLoading()
                }
            }
        })

        listViewModel.getUserState.observe(viewLifecycleOwner, Observer {
            when(it) {
                is RequestState.Loading -> {
                    showLoading()
                }
                is RequestState.Success -> {
                    tvHomeHelloUser.text = it.data
                    hideLoading()
                }
            }
        })
    }
}