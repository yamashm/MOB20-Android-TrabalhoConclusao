package br.com.fiap.mob20_android_trabalhoconclusao.presentation.home

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.mob20_android_trabalhoconclusao.R
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.ListItem
import br.com.fiap.mob20_android_trabalhoconclusao.presentation.base.auth.BaseAuthFragment
import br.com.fiap.mob20_android_trabalhoconclusao.presentation.login.MapsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
class HomeFragment : BaseAuthFragment() {
    override val layout = R.layout.fragment_home

    private lateinit var nvHome: BottomNavigationView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBackPressedAction()

        setUpView(view)
    }

    private fun setUpView(view: View) {
        nvHome = view.findViewById(R.id.nvHome)

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
                R.id.navigation_list -> {

                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
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