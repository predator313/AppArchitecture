package com.example.apparchitecture.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.apparchitecture.R
import com.example.apparchitecture.commons.EXTRA_DESC
import com.example.apparchitecture.commons.EXTRA_ID
import com.example.apparchitecture.commons.EXTRA_PRIORITY
import com.example.apparchitecture.commons.EXTRA_TITLE
import com.example.apparchitecture.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var text="Add Note"
        if(intent.hasExtra(EXTRA_ID)){
            text="Edit Note"

            //also do the other stuff
            binding.etTitle.setText(intent.getStringExtra(EXTRA_TITLE) ?: "Title")
            binding.etDesc.setText(intent.getStringExtra(EXTRA_DESC) ?: "Desc")
            binding.priorityPicker.setText(intent.getIntExtra(EXTRA_PRIORITY,1).toString())
        }
        supportActionBar?.apply {
            title=text
            setIcon(R.drawable.ic_close)
            setDisplayHomeAsUpEnabled(true)
        }

    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu,menu)
        return true
    }

    //this function when we click the menu items
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.save_note->{
                saveNote()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun saveNote(){
        val title=binding.etTitle.text.toString()
        val desc=binding.etDesc.text.toString()
        val priority=binding.priorityPicker.text.toString().toInt()

        if(title.trim().isEmpty() || desc.trim().isEmpty()){
            Toast.makeText(this@AddNoteActivity,"input fields can't be empty",
                Toast.LENGTH_SHORT).show()
            return
        }
        val id=intent.getIntExtra(EXTRA_ID, -1)
        val dataIntent=Intent()
        dataIntent.apply {
            putExtra(EXTRA_TITLE,title)
            putExtra(EXTRA_DESC,desc)
            putExtra(EXTRA_PRIORITY,priority)
            if(id!=-1)putExtra(EXTRA_ID, id)
            setResult(RESULT_OK,this)
            finish()
        }

    }
}