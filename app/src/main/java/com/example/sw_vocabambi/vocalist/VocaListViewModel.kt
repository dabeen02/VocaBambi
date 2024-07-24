package com.example.wordbook.vocalist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.wordbook.database.Word
import com.example.wordbook.database.getDatabase
import com.example.wordbook.repository.WordRepository
import kotlinx.coroutines.launch

class VocaListViewModel(application: Application): AndroidViewModel(application) {
    private val repository = WordRepository(getDatabase(application))

    val vocas: MutableLiveData<List<Word>> = MutableLiveData()

    init {
        // 초기에 모든 단어를 가져와서 단어 목록을 설정합니다.
        getAllWords()
    }

    fun searchWords(query: String) {
        viewModelScope.launch {
            val searchResults = if (query.isBlank()) {
                repository.getWordList()
            } else {
                repository.searchWords(query)
            }
            vocas.postValue(searchResults.reversed())
        }
    }

    private fun getAllWords() {
        viewModelScope.launch {
            val allWords = repository.getWordList()
            vocas.postValue(allWords.reversed())
        }
    }
}