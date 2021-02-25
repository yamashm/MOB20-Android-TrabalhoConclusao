package br.com.fiap.mob20_android_trabalhoconclusao.ui.base.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.mob20_android_trabalhoconclusao.data.remote.datasource.UserRemoteFakeDataSourceImpl
import br.com.fiap.mob20_android_trabalhoconclusao.data.repository.UserRepositoryImpl
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetUserLoggedUseCase
import br.com.fiap.mob20_android_trabalhoconclusao.ui.base.BaseFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

const val NAVIGATION_KEY = "NAV_KEY"

@ExperimentalCoroutinesApi
abstract class BaseAuthFragment : BaseFragment() {
    private val baseAuthViewModel: BaseAuthViewModel by lazy {
        ViewModelProvider(
            this,
            BaseViewModelFactory(
                GetUserLoggedUseCase(
                    UserRepositoryImpl(UserRemoteFakeDataSourceImpl())
                )
            )
        ).get(BaseAuthViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registerObserver()
        baseAuthViewModel.getUserLogged()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun registerObserver() {
        baseAuthViewModel.userLogged.observe(viewLifecycleOwner, Observer {
                result ->
            when (result) {
                is RequestState.Loading -> {
                    showLoading()
                }
                is RequestState.Success -> {
                    hideLoading()
                }
                is RequestState.Error -> {
                    hideLoading()
                }
            }
        })
    }
}