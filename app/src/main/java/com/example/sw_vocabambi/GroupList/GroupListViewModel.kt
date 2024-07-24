package com.example.wordbook.grouplist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.wordbook.database.getDatabaseGroupList
import com.example.wordbook.repository.GroupListRepository

class GroupListViewModel(application: Application):AndroidViewModel(application) {
    private val repository = GroupListRepository(getDatabaseGroupList(application))
    //얘를 이용해 data 얻을 수 있음
    //viewModel에서 인자 받고싶으면 ModelFactory 커스텀 필요함
    val groups = repository.getGroupListByLiveData()

    suspend fun deleteGroup(groupId: Int) {
        val group = repository.findByGroup(groupId)
        return repository.deletegroup(group)
    }
}