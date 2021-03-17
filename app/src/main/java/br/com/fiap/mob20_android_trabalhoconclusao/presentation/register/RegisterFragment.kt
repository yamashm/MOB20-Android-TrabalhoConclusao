package br.com.fiap.mob20_android_trabalhoconclusao.presentation.register

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.mob20_android_trabalhoconclusao.R
import br.com.fiap.mob20_android_trabalhoconclusao.data.remote.datasource.ItemRemoteFirebaseDataSourceImpl
import br.com.fiap.mob20_android_trabalhoconclusao.data.remote.datasource.UserRemoteFirebaseDataSourceImpl
import br.com.fiap.mob20_android_trabalhoconclusao.data.repository.ItemRepositoryImpl
import br.com.fiap.mob20_android_trabalhoconclusao.data.repository.UserRepositoryImpl
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetItemsUseCase
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetUserLoggedUseCase
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.SaveItemUseCase
import br.com.fiap.mob20_android_trabalhoconclusao.extensions.getString
import br.com.fiap.mob20_android_trabalhoconclusao.presentation.base.auth.BaseAuthFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterFragment : BaseAuthFragment() {
    override val layout = R.layout.fragment_register

    private lateinit var btRegister : Button

    private lateinit var etNameItem: EditText
    private lateinit var etLocationItem: EditText
    private lateinit var etDescriptionItem: EditText


    private val registerViewModel: RegisterViewModel by lazy {
        ViewModelProvider(
                this,
                RegisterViewModelFactory(
                        SaveItemUseCase(
                                GetUserLoggedUseCase(
                                        UserRepositoryImpl(
                                                UserRemoteFirebaseDataSourceImpl(
                                                        FirebaseAuth.getInstance(),
                                                        FirebaseFirestore.getInstance()
                                                )
                                        )
                                ),
                                ItemRepositoryImpl(
                                        ItemRemoteFirebaseDataSourceImpl(
                                                FirebaseFirestore.getInstance()
                                        )
                                )
                        ),
                        GetItemsUseCase(
                                ItemRepositoryImpl(
                                        ItemRemoteFirebaseDataSourceImpl(FirebaseFirestore.getInstance())
                                )
                        )
                )
        ).get( RegisterViewModel ::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView(view)
        setUpListener()
        registerObserver()
    }

    private fun setUpView(view: View) {
        btRegister = view.findViewById(R.id.btRegister)
        etDescriptionItem = view.findViewById(R.id.etDescriptionItem)
        etLocationItem = view.findViewById(R.id.etLocationItem)
        etNameItem = view.findViewById(R.id.etNameItem)

    }

    private fun setUpListener() {
        btRegister.setOnClickListener{
            registerViewModel.saveItem(
                    etNameItem.getString(), etLocationItem.getString(), etDescriptionItem.getString()
            )
        }
    }

    private fun registerObserver() {
        registerViewModel.itemsSelectedState.observe(viewLifecycleOwner, Observer {
            when(it){
                is RequestState.Success -> {
                    val items = it.data

                    hideLoading()
                }
                is RequestState.Error -> {
                    hideLoading()
                }
                is RequestState.Loading -> {
                    showLoading("Aguarde um momento")
                }
            }
        })
    }
}