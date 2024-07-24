package com.example.wordbook.test

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class TestWordGoalViewModel(application: Application): AndroidViewModel(application) {

    //현재 단어 목표 개수
    private val _mTestWordGoal = MutableLiveData<Int>()
    val mTestWordGoal: LiveData<Int>
        get() = _mTestWordGoal

    fun wordGoalDb(selectedRange: Int) {
        _mTestWordGoal.value = selectedRange
    }

}

















