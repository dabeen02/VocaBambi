package com.example.wordbook.groupword

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordbook.database.GroupWord
import com.example.wordbook.database.getDatabaseGroupList
import com.example.wordbook.repository.GroupListRepository
import kotlinx.coroutines.launch

//GroupId 받아서 처리를 해야함 GroupId -> GroupWordList로.
class GroupWordViewModel(application: Application, val mGroupId: Int):ViewModel() {
    private val repository = GroupListRepository(getDatabaseGroupList(application))

    val groupWordList: MutableLiveData<List<GroupWord>> = MutableLiveData()

    suspend fun deleteGroupWord(groupword: GroupWord) {
        return repository.deleteGroupWord(groupword)
    }
    init {
        // 초기에 모든 단어를 가져와서 단어 목록을 설정합니다.
        getAllWords()
    }

    fun searchWords(query: String) {
        viewModelScope.launch {
            val searchResults = if (query.isBlank()) {
                repository.getWordList(mGroupId)
            } else {
                repository.searchWords(query, mGroupId)
            }
            groupWordList.postValue(searchResults.reversed())
        }
    }

    private fun getAllWords() {
        viewModelScope.launch {
            val allWords = repository.getWordList(mGroupId)
            groupWordList.postValue(allWords.reversed())
        }
    }

    fun getWordList(){
        viewModelScope.launch {
            val allword = repository.getWordList(mGroupId)
            groupWordList.postValue(allword.reversed())
        }
    }
}