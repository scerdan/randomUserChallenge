package com.example.challenge_random_user.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge_random_user.data.repository.UserRepositoryImpl
import com.example.challenge_random_user.presentation.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.challenge_random_user.domain.models.Result


@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepositoryImpl: UserRepositoryImpl
) : ViewModel() {

    private val _state = MutableStateFlow(UserState())
    val state: StateFlow<UserState> = _state
    private val _allUsers = arrayListOf<Result>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getUsers()
        }
    }

    private suspend fun getUsers() {
        viewModelScope.launch {
            val users = userRepositoryImpl.getRandomUser()
            when (users.code()) {
                200 -> {
                    if (users.isSuccessful) {
                        users.body()?.results?.map { onlyUser ->
                            _allUsers.add(onlyUser)
                        }
                    }
                    _state.value = UserState(allUsers = _allUsers)
                }
                else -> {}
            }
        }
    }
}