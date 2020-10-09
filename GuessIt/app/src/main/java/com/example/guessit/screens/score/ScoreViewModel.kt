package com.example.guessit.screens.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore: Int) : ViewModel() {
    private val _eventPlayAgain: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val eventPlayAgain: LiveData<Boolean> by lazy { _eventPlayAgain }

    private val _score: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    val score: LiveData<Int> by lazy { _score }


    init {
        _score.value = finalScore
    }

    fun onPlayAgain() {
        _eventPlayAgain.value = true
    }

    fun onPlayAgainComplete() {
        _eventPlayAgain.value = false
    }


}


