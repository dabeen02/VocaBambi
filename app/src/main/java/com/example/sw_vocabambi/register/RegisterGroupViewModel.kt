package com.example.wordbook.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordbook.database.Group
import com.example.wordbook.database.getDatabaseGroupList
import com.example.wordbook.repository.GroupListRepository
import kotlinx.coroutines.launch

class RegisterGroupViewModel(application: Application):AndroidViewModel(application) {
    private val repository = GroupListRepository(getDatabaseGroupList(application))

    fun registerGroup(group: Group){
        viewModelScope.launch {
            repository.insertgroup(group)
        }
    }
}


