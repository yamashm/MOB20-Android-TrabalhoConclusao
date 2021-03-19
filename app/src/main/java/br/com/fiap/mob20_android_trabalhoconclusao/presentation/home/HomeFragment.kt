package br.com.fiap.mob20_android_trabalhoconclusao.presentation.home

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.fiap.mob20_android_trabalhoconclusao.R
import br.com.fiap.mob20_android_trabalhoconclusao.data.remote.datasource.UserRemoteFirebaseDataSourceImpl
import br.com.fiap.mob20_android_trabalhoconclusao.data.repository.UserRepositoryImpl
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetUserLoggedUseCase
import br.com.fiap.mob20_android_trabalhoconclusao.presentation.base.auth.BaseAuthFragment
import br.com.fiap.mob20_android_trabalhoconclusao.presentation.maps.MapsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
class HomeFragment : BaseAuthFragment() {
    override val layout = R.layout.fragment_home

    private lateinit var nvHome: BottomNavigationView
    private lateinit var btAdd: Button

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(
                this,
                HomeViewModelFactory(
                        GetUserLoggedUseCase(
                                UserRepositoryImpl(
                                        UserRemoteFirebaseDataSourceImpl(
                                                FirebaseAuth.getInstance(),
                                                FirebaseFirestore.getInstance()
                                        )
                                )
                        )
                )
        ).get(HomeViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBackPressedAction()
        setUpView(view)
        homeViewModel.getUser()
    }

    private fun setUpView(view: View) {
        nvHome = view.findViewById(R.id.nvHome)
        btAdd = view.findViewById(R.id.btAdd)

        loadFragment(ListFragment())

        nvHome.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_list -> {
                    loadFragment(ListFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_map -> {
                    loadFragment(MapsFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_exit -> {
                    logout()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }

        btAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_registerFragment);

        }
    }

    private fun loadFragment(fragment: Fragment) {
        (activity as AppCompatActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
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