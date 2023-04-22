package com.example.challenge_random_user.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.challenge_random_user.domain.models.Result

class SharedViewmodel: ViewModel() {

    var clickedUser by mutableStateOf<Result?>(null)
    private set

    fun addValue(newUser: Result) {
        clickedUser = newUser
    }
}