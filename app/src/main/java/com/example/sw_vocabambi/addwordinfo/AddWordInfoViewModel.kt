package com.example.wordbook.addwordinfo

import android.app.Application
import android.location.Geocoder.isPresent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordbook.database.*
import com.example.wordbook.repository.GroupListRepository
import com.example.wordbook.repository.WordRepository
import kotlinx.coroutines.launch

// taggedUnion
sealed class AddWordInfoViewModelState {
    object Loading : AddWordInfoViewModelState()
    class Ready(val word: Word): AddWordInfoViewModelState()
}

class AddWordInfoViewModel(application: Application, val mWordId: Int, val mGroupId: Int): ViewModel() {
    //Word DB얻음
    private val repositoryWord = WordRepository(getDatabase(application))
    private val repositoryGroup = GroupListRepository(getDatabaseGroupList(application))

    private var _state = MutableLiveData<AddWordInfoViewModelState>(AddWordInfoViewModelState.Loading)
    val state: LiveData<AddWordInfoViewModelState>
        get() = _state

    init {
        load(mWordId)
    }

    suspend fun insertGroupWord(): Long {
        if(mWordId == -1 || mGroupId == -1) {
            return -1
        }
        val word = repositoryWord.findById(mWordId)
        return repositoryGroup.insertGroupWord(GroupWord.make(mGroupId, word.english, word.means))
    }
    suspend fun checkWordInGroupWord(mWordId: Int): Boolean{
        val groupwordlist = repositoryGroup.getGroupWordEnglishList(mGroupId)
        val word = repositoryWord.findById(mWordId)
        if(groupwordlist.contains(word.english)){
            return true
        }
        return false
    }

    //word Id 있는지 확인하는 작업
    private fun load(wordId: Int) {
        if (wordId == -1) {
            return
        }
        viewModelScope.launch {
            val word = repositoryWord.findById(wordId)

            _state.value = AddWordInfoViewModelState.Ready(word)
        }
    }
}