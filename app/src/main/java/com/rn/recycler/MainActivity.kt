package com.rn.recycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.rn.recycler.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private var messages = mutableListOf<Message>()
    private var adapter = MessageAdapter(messages, this::onMessageItemClick)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lastCustomNonConfigurationInstance.let { savedMessages ->
            if(savedMessages is MutableList<*>){
                messages.addAll(savedMessages.filterIsInstance(Message::class.java))
            }
        }
        initRecyclerView()
        binding.fabAdd.setOnClickListener{
            addMessage()
        }

    }

    private fun initRecyclerView(){
        binding.rvMessages.adapter = adapter
        var layoutManager = GridLayoutManager(this, 2)
        layoutManager.spanSizeLookup = object :
        GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
               return if(position == 0) 2 else 1
            }
        }

        binding.rvMessages.layoutManager = layoutManager

        initSwipeGesture()
    }

    private fun addMessage(){
        val message = Message(binding.edtTitle.text.toString(), binding.edtText.text.toString())
        messages.add(message)
        adapter.notifyItemInserted(messages.lastIndex)
        binding.edtTitle.text?.clear()
        binding.edtText.text?.clear()
        binding.edtTitle.requestFocus()
    }

    private fun initSwipeGesture(){
        val swipe = object:ItemTouchHelper.SimpleCallback(
            0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                messages.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipe)
        itemTouchHelper.attachToRecyclerView(binding.rvMessages)
    }

    private fun onMessageItemClick(message:Message){
        val s = "${message.title}\n${message.text}"
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return messages
    }

}