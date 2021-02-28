package br.com.fiap.mob20_android_trabalhoconclusao.presentation.home

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import br.com.fiap.mob20_android_trabalhoconclusao.R
import br.com.fiap.mob20_android_trabalhoconclusao.presentation.base.auth.BaseAuthFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class HomeFragment : BaseAuthFragment() {
    override val layout = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBackPressedAction()
    }
    private fun registerBackPressedAction() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            callback)
    }
}