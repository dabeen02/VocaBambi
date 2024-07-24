package com.example.wordbook.groupword

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GroupWordViewModelFactory (private val application: Application,
                                 private val groupId: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GroupWordViewModel::class.java)) {
            return GroupWordViewModel(application, groupId) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}