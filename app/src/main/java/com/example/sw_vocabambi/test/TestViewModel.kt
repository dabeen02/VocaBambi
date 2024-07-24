package com.example.wordbook.test

import android.app.Application
import androidx.lifecycle.*
import com.example.wordbook.database.Word
import com.example.wordbook.database.getDatabase
import com.example.wordbook.repository.WordRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestViewModel(application: Application): AndroidViewModel(application) {

    data class TestUnit(val question: Word, val candidates: List<Word>)

    /*enum class ResultState {
        NONE,
        CORRECT,
        WRONG
    }*/


    companion object {
        private val DELAY_TIME_SHOWING_NEXT_TEST = 800L
    }


    private val repository = WordRepository(getDatabase(application))


    //추가한 부분
    var correctCount = 0 // 정답 개수를 저장할 변수
    var testCount = 70 //단어목표설정 값 일단 70으로 둠 // 남은 문제 개수를 저장할 변수//



    private val _mTestUnit = MutableLiveData<TestUnit>()
    val mTestUnit: LiveData<TestUnit>
        get() = _mTestUnit

    /*private val _mResultState = MutableLiveData(ResultState.NONE)
    val mResultState: LiveData<ResultState>
        get() = _mResultState*/



    //추가한 부분
    // 현재까지 정답 개수
    private val _mTestcorrectCount = MutableLiveData<Int>()
    val mTestcorrectCount: LiveData<Int>
        get() = _mTestcorrectCount

    //현재까지 남은 문제 횟수
    private val _mTestCount = MutableLiveData<Int>()
    val mTestCount: LiveData<Int>
        get() = _mTestCount



    init {
        loadNextTestUnit()
    }




    //수정한 부분
    private fun loadNextTestUnit() {
        //testCount--
        viewModelScope.launch {
            if (testCount <= 0){
                _mTestcorrectCount.value = correctCount
                _mTestCount.value = testCount
                return@launch
            }

            val shuffledList = repository.getWordList().shuffled()
            _mTestUnit.value = TestUnit(shuffledList[0], shuffledList.subList(0, 4).shuffled())
            //_mResultState.value = ResultState.NONE
            _mTestCount.value = testCount
            testCount--// 문제 개수 감소
        }
        //_mTestCount.value = testCount
    }



    fun onClickCandidate(candidateIdx: Int) {

        //만약에 candidateIdx가 4인 경우(모르겠음을 선택한 경우)
        if (candidateIdx == 4){
            viewModelScope.launch {
                delay(DELAY_TIME_SHOWING_NEXT_TEST)
                loadNextTestUnit()
            }

        } else{ //아니면 정답 확인
            when (isCorrect(candidateIdx)) {
                true -> {
                    //_mResultState.value = ResultState.CORRECT
                    correctCount++ // 정답 개수 증가
                    _mTestcorrectCount.value = correctCount // 현재 정답 개수 저장
                    viewModelScope.launch {
                        delay(DELAY_TIME_SHOWING_NEXT_TEST)
                        loadNextTestUnit()
                    }
                }
                false -> {
                    //_mResultState.value = ResultState.WRONG
                    viewModelScope.launch {
                        delay(DELAY_TIME_SHOWING_NEXT_TEST)
                        loadNextTestUnit()
                    }
                }
            }
        }

    }

    private fun isCorrect(candidateIdx: Int): Boolean {
        _mTestUnit.value?.let {
            return it.candidates[candidateIdx].id == it.question.id
        }

        return false
    }

}