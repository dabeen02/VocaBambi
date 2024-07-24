package com.example.wordbook.edit

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordbook.database.Word
import com.example.wordbook.database.getDatabase
import com.example.wordbook.repository.WordRepository
import kotlinx.coroutines.launch

// taggedUnion
sealed class EditVocaViewModelState {
    object Loading : EditVocaViewModelState()
    class Ready(val word: Word): EditVocaViewModelState()
}

class EditVocaViewModel(application: Application, val mWordId: Int): ViewModel() {
    private val repository = WordRepository(getDatabase(application))

    private var _state = MutableLiveData<EditVocaViewModelState>(EditVocaViewModelState.Loading)
    val state: LiveData<EditVocaViewModelState>
        get() = _state

    init {
        load(mWordId)
    }

    suspend fun getWordByEnglish(english: String): Word? {
        return repository.getWordByEnglish(english)
    }

    suspend fun updateWord(english: String, means: String): Int {
        if (mWordId == -1) {
            return -1
        }

        return repository.update(Word.make(mWordId, english, means))
    }

    private fun load(wordId: Int) {
        if (wordId == -1) {
            return
        }

        viewModelScope.launch {
            val word = repository.findById(wordId)

            _state.value = EditVocaViewModelState.Ready(word)
        }
    }

    //delete
    suspend fun deleteWord(): Int {
        if (mWordId == -1) {
            return -1
        }
        val word = repository.findById(mWordId)
        return repository.delete(word)
    }

}