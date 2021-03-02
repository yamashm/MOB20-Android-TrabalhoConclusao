package br.com.fiap.mob20_android_trabalhoconclusao.presentation.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.mob20_android_trabalhoconclusao.R
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.ListItem
import br.com.fiap.mob20_android_trabalhoconclusao.presentation.base.BaseFragment

class ListFragment : BaseFragment() {
    override val layout = R.layout.fragment_list

    private lateinit var rvHomeList: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
}