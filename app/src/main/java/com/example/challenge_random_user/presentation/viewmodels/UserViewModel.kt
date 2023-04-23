package com.example.challenge_random_user.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.challenge_random_user.data.repository.UserRepositoryImpl
import com.example.challenge_random_user.domain.models.Result
import com.example.challenge_random_user.presentation.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepositoryImpl: UserRepositoryImpl
) : ViewModel() {
    private var pageCount: Int = 1
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    private val _state = MutableStateFlow(UserState())
    val state: StateFlow<UserState> = _state
    private val _allUsers = arrayListOf<Result>()

    init {
        scope.launch {
            getUsers()
        }
    }

    private suspend fun getUsers() {
        val users = userRepositoryImpl.getRandomUser(pageCount)

        when (users.code()) {
            200 -> {
                if (users.isSuccessful) {
                    val newUsers = users.body()?.results?.distinct()
                    if (newUsers != null) {
                        _allUsers.addAll(newUsers)
                    }
                }
                _state.value = UserState(allUsers = _allUsers)
            }
            else -> {
                _state.value = UserState(error = users.errorBody().toString())
            }
        }
    }

    suspend fun fetchNextPageData() {
            pageCount++
            getUsers()
    }
}