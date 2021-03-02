package br.com.fiap.mob20_android_trabalhoconclusao.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.mob20_android_trabalhoconclusao.R
import br.com.fiap.mob20_android_trabalhoconclusao.data.remote.datasource.AppRemoteFirebaseDataSourceImpl
import br.com.fiap.mob20_android_trabalhoconclusao.data.repository.AppRepositoryImpl
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetMinAppVersionUseCase

abstract class BaseFragment : Fragment() {
    abstract val layout: Int
    private lateinit var loadingView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val screenRootView = FrameLayout(requireContext())
        val screenView = inflater.inflate(layout, container, false)
        loadingView = inflater.inflate(R.layout.include_loading, container, false)
        screenRootView.addView(screenView)
        screenRootView.addView(loadingView)
        return screenRootView
    }
    fun showLoading(message: String = "Processando a requisição") {
        loadingView.visibility = View.VISIBLE
        if (message.isNotEmpty())
            loadingView.findViewById<TextView>(R.id.tvLoading).text = message
    }
    fun hideLoading() {
        loadingView.visibility = View.GONE
    }
    fun showMessage(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}