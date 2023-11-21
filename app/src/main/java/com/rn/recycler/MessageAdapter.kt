package com.rn.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rn.recycler.databinding.ItemMessageBinding

class MessageAdapter(
    private val messages: List<Message>,
    private val callback: (Message) -> Unit
) :
    RecyclerView.Adapter<MessageAdapter.VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdapter.VH {
        val binding: ItemMessageBinding
        binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val vh = VH(binding)
        vh.itemView.setOnClickListener {
            val message = messages[vh.bindingAdapterPosition]
            callback(message)
        }

        return vh

    }

    override fun onBindViewHolder(holder: MessageAdapter.VH, position: Int) {
        val (title, text) = messages[position]
        holder.txtTitle.text = title
        holder.txtTexto.text = text
    }

    override fun getItemCount(): Int = messages.size

    class VH(binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        val txtTitle: TextView = binding.txtTitle
        val txtTexto: TextView = binding.txtText
    }

}