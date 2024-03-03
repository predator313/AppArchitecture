package com.example.apparchitecture.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apparchitecture.R
import com.example.apparchitecture.adapter.MyAdapter
import com.example.apparchitecture.commons.ADD_NOTE_REQUEST
import com.example.apparchitecture.commons.EDIT_NOTE_REQUEST
import com.example.apparchitecture.commons.EXTRA_DESC
import com.example.apparchitecture.commons.EXTRA_ID
import com.example.apparchitecture.commons.EXTRA_PRIORITY
import com.example.apparchitecture.commons.EXTRA_TITLE
import com.example.apparchitecture.databinding.ActivityMainBinding
import com.example.apparchitecture.db.table.Note
import com.example.apparchitecture.ui.viewmodel.NoteViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: MyAdapter


    private val viewModel: NoteViewModel by viewModels()

    //    private var list= listOf(Note("Title1","this is title1",1))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myAdapter = MyAdapter { note ->
            Intent(this, AddNoteActivity::class.java).apply {
                putExtra(EXTRA_TITLE, note.title)
                putExtra(EXTRA_DESC, note.desc)
                putExtra(EXTRA_PRIORITY, note.priority)
                putExtra(EXTRA_ID, note.id)
                startActivityForResult(this, EDIT_NOTE_REQUEST)
            }
        }
        setUpAdapter()
        viewModel.allNotes.observe(this, Observer {
            Log.e("hello", "observable,size ${it.size}")
            myAdapter.submitList(it)
        })
        binding.btnAdd.setOnClickListener {
            addNote()
        }
    }

    private fun setUpAdapter() = binding.rvMain.apply {
        adapter = myAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.rvMain)

    }

    private fun addNote() {
        val intent = Intent(this@MainActivity, AddNoteActivity::class.java)
        startActivityForResult(intent, ADD_NOTE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            val title = data?.getStringExtra(EXTRA_TITLE) ?: "Title"
            val desc = data?.getStringExtra(EXTRA_DESC) ?: "Desc"
            val priority = data?.getIntExtra(EXTRA_PRIORITY, 1)
            val note = Note(
                title,
                desc,
                priority!!
            )
            viewModel.insertNotes(note)

        }
        else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK){
            val id=data?.getIntExtra(EXTRA_ID, -1)
            if(id==-1)return;
            val title = data?.getStringExtra(EXTRA_TITLE) ?: "Title"
            val desc = data?.getStringExtra(EXTRA_DESC) ?: "Desc"
            val priority = data?.getIntExtra(EXTRA_PRIORITY, 1)
            val note=Note(
                title,
                desc,
                priority!!,
                id
            )
             viewModel.updateNotes(note)
        }
    }

    private val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false  // we do nothing while dragging to top or bottom
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val note = myAdapter.currentList[position]
            viewModel.deleteNote(note)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.delete_all_notes, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_all_items_menu -> {
                viewModel.deleteAllNotes()
                Log.e("aamir", "hello world")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}