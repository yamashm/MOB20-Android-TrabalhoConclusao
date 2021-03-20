package br.com.fiap.mob20_android_trabalhoconclusao.presentation.register

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import br.com.concrete.canarinho.watcher.TelefoneTextWatcher
import br.com.concrete.canarinho.watcher.evento.EventoDeValidacao
import br.com.fiap.mob20_android_trabalhoconclusao.R
import br.com.fiap.mob20_android_trabalhoconclusao.data.remote.datasource.ItemRemoteFirebaseDataSourceImpl
import br.com.fiap.mob20_android_trabalhoconclusao.data.remote.datasource.UserRemoteFirebaseDataSourceImpl
import br.com.fiap.mob20_android_trabalhoconclusao.data.repository.ItemRepositoryImpl
import br.com.fiap.mob20_android_trabalhoconclusao.data.repository.UserRepositoryImpl
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.Item
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.User
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.*
import br.com.fiap.mob20_android_trabalhoconclusao.extensions.getString
import br.com.fiap.mob20_android_trabalhoconclusao.presentation.base.auth.BaseAuthFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterFragment : BaseAuthFragment() {
    override val layout = R.layout.fragment_register

    private lateinit var btRegister : Button

    private lateinit var etNameItem: EditText
    private lateinit var etPhoneItem: EditText
    private lateinit var etLocationItem: EditText
    private lateinit var etDescriptionItem: EditText

    private lateinit var itemId: String

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
                                            FirebaseAuth.getInstance(),
                                                FirebaseFirestore.getInstance()
                                        )
                                )
                        ),

                        GetItemUseCase(
                                ItemRepositoryImpl(
                                        ItemRemoteFirebaseDataSourceImpl(
                                                FirebaseAuth.getInstance(),
                                                FirebaseFirestore.getInstance())
                                )
                        ),

                        UpdateItemUseCase(
                                ItemRepositoryImpl(
                                        ItemRemoteFirebaseDataSourceImpl(
                                            FirebaseAuth.getInstance(),
                                            FirebaseFirestore.getInstance())
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

        var itemIdArg = arguments?.getString("itemId")

        if(itemIdArg != null){
            itemId = itemIdArg
        } else {
            itemId = ""
        }

         if(itemId.isNotEmpty()){
                registerViewModel.getItem(itemId)
        }
    }

    private fun setUpView(view: View) {
        btRegister = view.findViewById(R.id.btRegister)
        etDescriptionItem = view.findViewById(R.id.etDescriptionItem)
        etPhoneItem = view.findViewById(R.id.etPhoneItem)
        etLocationItem = view.findViewById(R.id.etLocationItem)
        etNameItem = view.findViewById(R.id.etNameItem)

    }

    private fun setUpListener() {
        etPhoneItem.addTextChangedListener(TelefoneTextWatcher(object : EventoDeValidacao {
            override fun totalmenteValido(valorAtual: String?) {}
            override fun invalido(valorAtual: String?, mensagem: String?) {}
            override fun parcialmenteValido(valorAtual: String?) {}
        }))
        btRegister.setOnClickListener{
            if(itemId == itemId) {
                registerViewModel.saveItem(
                        etNameItem.getString(),
                        etLocationItem.getString(),
                        etPhoneItem.getString(),
                        etDescriptionItem.getString()
                )
            } else {
                registerViewModel.updateItem( etNameItem.getString(),
                        etLocationItem.getString(),
                        etPhoneItem.getString(),
                        etDescriptionItem.getString(), itemId)
            }
        }
    }

    private fun registerObserver() {
        registerViewModel.itemSaveState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()
                    NavHostFragment.findNavController(this)
                            .navigate(R.id.main_nav_graph)
                }
                is RequestState.Loading -> {
                    showLoading("Aguarde um momento")
                }
                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.throwable.message)
                }
                is RequestState.Loading -> showLoading("Salvando item")
            }
        })

        registerViewModel.getItemState.observe(viewLifecycleOwner, Observer {
            when(it) {
                is RequestState.Success -> {
                    hideLoading()

                    etNameItem.setText(it.data.name)
                    etLocationItem.setText(it.data.location)
                    etPhoneItem.setText(it.data.phone)
                    etDescriptionItem.setText(it.data.description)
                }
                is RequestState.Error -> {
                    hideLoading()
                }
                is RequestState.Loading -> {
                    showLoading("Aguarde um momento")
                }
            }
        })

        registerViewModel.itemUpdateState.observe(viewLifecycleOwner, Observer {
            when(it){
                is RequestState.Success -> {
                    hideLoading()

                    NavHostFragment.findNavController(this)
                            .navigate(R.id.main_nav_graph)
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