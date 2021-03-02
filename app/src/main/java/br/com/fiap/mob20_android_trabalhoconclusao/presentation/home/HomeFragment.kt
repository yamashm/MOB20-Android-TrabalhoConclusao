package br.com.fiap.mob20_android_trabalhoconclusao.presentation.home

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.mob20_android_trabalhoconclusao.R
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.ListItem
import br.com.fiap.mob20_android_trabalhoconclusao.presentation.base.auth.BaseAuthFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class HomeFragment : BaseAuthFragment() {
    override val layout = R.layout.fragment_home

    private lateinit var rvHomeList: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBackPressedAction()

        setUpView(view)
        var item: ListItem = ListItem("TESTE", "","")

        var list = listOf<ListItem>(item, item, item, item, item, item)

        setUpList(list)
    }

    private fun setUpView(view: View) {
        rvHomeList = view.findViewById(R.id.rvHomeList)
    }

    private fun setUpList(items: List<ListItem>) {
        rvHomeList.adapter = HomeAdapter(items, this::clickItem)
    }

    private fun clickItem(item: ListItem) {

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