package br.com.fiap.mob20_android_trabalhoconclusao.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import br.com.fiap.mob20_android_trabalhoconclusao.R
import br.com.fiap.mob20_android_trabalhoconclusao.data.remote.datasource.AppRemoteFirebaseDataSourceImpl
import br.com.fiap.mob20_android_trabalhoconclusao.data.repository.AppRepositoryImpl
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetMinAppVersionUseCase
import br.com.fiap.mob20_android_trabalhoconclusao.presentation.base.auth.NAVIGATION_KEY

abstract class BaseFragment : Fragment() {
    abstract val layout: Int
    private lateinit var loadingView: View

    private val baseViewModel: BaseViewModel by lazy {
        ViewModelProvider(
                this,
                BaseViewModelFactory(
                        GetMinAppVersionUseCase(
                                AppRepositoryImpl(
                                        AppRemoteFirebaseDataSourceImpl()
                                )
                        )
                )
        ).get(BaseViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val screenRootView = FrameLayout(requireContext())
        val screenView = inflater.inflate(layout, container, false)

        loadingView = inflater.inflate(R.layout.include_loading, container, false)

        val flavourScreen = inflater.inflate(R.layout.include_flavour, container, false)
        val flavourView = flavourScreen.findViewById<LinearLayout>(R.id.flavourScreen)

        screenRootView.addView(screenView)
        screenRootView.addView(loadingView)
        screenRootView.addView(flavourView)

        return screenRootView
    }

//    private fun configureEnvironment(container: View, tvEnvironment: TextView) {
//
//        when (BuildConfig.FLAVOR) {
//            "dev" -> {
//                container.visibility = View.VISIBLE
//                tvEnvironment.text = "Desenvolvimento"
//            }
//            "hml" -> {
//                container.visibility = View.VISIBLE
//                tvEnvironment.text = "Homologação"
//            }
//            "main" -> {
//                container.visibility = View.GONE
//                tvEnvironment.text = ""
//            }
//        }
//
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerObserver()
    }

    private fun registerObserver() {
        baseViewModel.minVersionAppState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Loading -> {
                    showLoading()
                }
                is RequestState.Success -> {
                    hideLoading()
//                    if (it.data > BuildConfig.VERSION_CODE) {
//                        startUpdateApp()
//                    }
                }
                is RequestState.Error -> {
                    NavHostFragment.findNavController(this).navigate(
                            R.id.login_nav_graph, bundleOf(
                            NAVIGATION_KEY to NavHostFragment.findNavController(this).currentDestination?.id
                    )
                    )
                    hideLoading()
                }
            }
        })
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