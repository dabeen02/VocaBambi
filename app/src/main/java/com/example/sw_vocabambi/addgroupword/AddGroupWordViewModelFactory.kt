package com.example.wordbook.addgroupword

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wordbook.addgroupword.AddGroupWordViewModel

class AddGroupWordViewModelFactory (private val application: Application,
                                    private val groupId: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddGroupWordViewModel::class.java)) {
            return AddGroupWordViewModel(application, groupId) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}