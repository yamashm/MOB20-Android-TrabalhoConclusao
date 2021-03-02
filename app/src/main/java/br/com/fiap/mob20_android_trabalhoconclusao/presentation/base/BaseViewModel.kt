package br.com.fiap.mob20_android_trabalhoconclusao.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetMinAppVersionUseCase
import kotlinx.coroutines.launch

class BaseViewModel(
        private val getMinAppVersionUseCase: GetMinAppVersionUseCase
) : ViewModel() {

    var minVersionAppState = MutableLiveData<RequestState<Int>>()
    fun getMinVersion() {
        viewModelScope.launch {
            minVersionAppState.value = getMinAppVersionUseCase.getMinVersionApp()
        }
    }
}