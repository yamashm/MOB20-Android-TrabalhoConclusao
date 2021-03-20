package br.com.fiap.mob20_android_trabalhoconclusao.presentation.integration

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.mob20_android_trabalhoconclusao.R
import br.com.fiap.mob20_android_trabalhoconclusao.data.remote.datasource.ItemRemoteFirebaseDataSourceImpl
import br.com.fiap.mob20_android_trabalhoconclusao.data.repository.ItemRepositoryImpl
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetItemUseCase
import br.com.fiap.mob20_android_trabalhoconclusao.presentation.base.auth.BaseAuthFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class IntegrationFragment : BaseAuthFragment(){
    override val layout = R.layout.fragment_integration

    private lateinit var tvInfoName: TextView
    private lateinit var tvInfoLocation: TextView
    private lateinit var tvInfoPhone: TextView
    private lateinit var tvInfoDescription: TextView

    private lateinit var btCall : Button
    private lateinit var btShare : Button

    private val integrationViewModel: IntegrationViewModel by lazy{
        ViewModelProvider(
            this,
            IntegrationViewModelFactory(
                GetItemUseCase(
                    ItemRepositoryImpl(
                        ItemRemoteFirebaseDataSourceImpl(
                            FirebaseAuth.getInstance(),
                            FirebaseFirestore.getInstance())
                    )
                )
            )
        ).get( IntegrationViewModel ::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView(view)
        setUpListener()
        registerObserver()
    }

    private fun setUpView(view: View) {
        btCall = view.findViewById(R.id.btCall)
        btShare = view.findViewById(R.id.btShare)

        tvInfoName = view.findViewById(R.id.tvInfoName)
        tvInfoLocation = view.findViewById(R.id.tvInfoLocation)
        tvInfoPhone = view.findViewById(R.id.tvInfoPhone)
        tvInfoDescription = view.findViewById(R.id.tvInfoDescription)

    }

    private fun setUpListener() {
        btCall.setOnClickListener{

        }

        btShare.setOnClickListener {

        }
    }

    private fun registerObserver() {
        integrationViewModel.getItemState.observe(viewLifecycleOwner, Observer {
            when(it) {
                is RequestState.Success -> {
                    hideLoading()

                    tvInfoName.setText(it.data.name)
                    tvInfoLocation.setText(it.data.location)
                    tvInfoPhone.setText(it.data.phone)
                    tvInfoDescription.setText(it.data.description)
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