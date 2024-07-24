package com.example.wordbook.main

import android.app.Application
import androidx.lifecycle.*
import com.example.wordbook.database.getDatabase
import com.example.wordbook.repository.WordRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


enum class MovingState {
    IDLE, MOVE, FAIL
}

class MainViewModel(application: Application): AndroidViewModel(application) {
    companion object {
        const val LIMIT_TO_MOVE_STUDY = 1
        const val LIMIT_TO_MOVE_TEST = 4
    }
    private val repository = WordRepository(getDatabase(application))

    private val _mStudyMovingState = MutableLiveData<MovingState>(MovingState.IDLE)
    val mStudyMovingState: LiveData<MovingState>
        get() = _mStudyMovingState


    private val _mTestMovingState = MutableLiveData<MovingState>(MovingState.IDLE)
    val mTestMovingState: LiveData<MovingState>
        get() = _mTestMovingState

    //추가한 부분
    private val _mTestWordGoalMovingState = MutableLiveData<MovingState>(MovingState.IDLE)
    val mTestWordGoalState: LiveData<MovingState>
        get() = _mTestWordGoalMovingState

    private val _mTestDateMovingState = MutableLiveData<MovingState>(MovingState.IDLE)
    val mTestDateMovingState: LiveData<MovingState>
        get() = _mTestDateMovingState

    //study 추가
    private val _mStudyDateMovingState = MutableLiveData<MovingState>(MovingState.IDLE)
    val mStudyDateMovingState: LiveData<MovingState>
        get() = _mStudyDateMovingState

    fun moveToStudy() {
        viewModelScope.launch {
            if (moveToStudyEnabled()) {
                _mStudyMovingState.value = MovingState.MOVE
            } else {
                _mStudyMovingState.value = MovingState.FAIL
            }
        }
    }

    fun setStudyMovingStateIdle() {
        _mStudyMovingState.value = MovingState.IDLE
    }

    fun moveToTest() {
        viewModelScope.launch {
            if (moveToTestEnabled()) {
                _mTestMovingState.value = MovingState.MOVE
            } else {
                _mTestMovingState.value = MovingState.FAIL
            }
        }
    }

    fun setTestMovingStateIdle() {
        _mTestMovingState.value = MovingState.IDLE
    }

    private suspend fun moveToStudyEnabled(): Boolean {
        return viewModelScope.async {
            repository.getCounts() >= LIMIT_TO_MOVE_STUDY
        }.await()
    }

    private suspend fun moveToTestEnabled(): Boolean {
        return viewModelScope.async {
            repository.getCounts() >= LIMIT_TO_MOVE_TEST
        }.await()
    }

    //학습 날짜

    private suspend fun moveToStudyDateEnabled(): Boolean {
        return viewModelScope.async {
            repository.getCounts() >= MainViewModel.LIMIT_TO_MOVE_STUDY
        }.await()
    }


    fun moveToStudyDate() {
        viewModelScope.launch {
            if (moveToStudyDateEnabled()) {
                _mStudyMovingState.value = MovingState.MOVE
            } else {
                _mStudyMovingState.value = MovingState.FAIL
            }
        }
    }

}