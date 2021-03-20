package br.com.fiap.mob20_android_trabalhoconclusao.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.mob20_android_trabalhoconclusao.R
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.ListItem

class HomeAdapter(
        private var listItems: List<ListItem>,
        private var clickListener: (ListItem) -> Unit,
        private var deleteClickListener: (String) -> Unit
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.list_item,
                parent, false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listItems[position], clickListener, deleteClickListener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
                item: ListItem, clickListener: (ListItem) -> Unit, deleteClickListener: (String) -> Unit
        ) {
            val nome = itemView.findViewById<TextView>(R.id.tvItemNome)
            val location = itemView.findViewById<TextView>(R.id.tvItemLocation)

            nome.text = item.name
            location.text = item.location

            val btDelete = itemView.findViewById<Button>(R.id.btDelete)

            btDelete.setOnClickListener{
                deleteClickListener(item.itemId)
            }

            itemView.setOnClickListener { clickListener(item) }
        }
    }
}