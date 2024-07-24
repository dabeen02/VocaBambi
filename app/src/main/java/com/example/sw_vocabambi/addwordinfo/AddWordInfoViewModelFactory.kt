package com.example.wordbook.addwordinfo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wordbook.edit.EditVocaViewModel

class AddWordInfoViewModelFactory(private val application: Application,
private val wordId: Int, private val groupId: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddWordInfoViewModel::class.java)) {
            return AddWordInfoViewModel(application, wordId, groupId) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}