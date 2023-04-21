package com.example.challenge_random_user.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge_random_user.domain.models.User
import com.example.challenge_random_user.domain.use_cases.GetRandomUserUseCase
import com.example.challenge_random_user.presentation.UserState
import com.example.challenge_random_user.utlis.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getRandomUserUseCase: GetRandomUserUseCase
) : ViewModel() {

    private val _state = mutableStateOf(UserState())
    val state: State<UserState> = _state
    private val _allUsers = arrayListOf<com.example.challenge_random_user.domain.models.Result>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getRandomUser()
        }
    }

    private fun getRandomUser() {
        getRandomUserUseCase.execute().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.body()?.results?.let { list ->
                        list.map {
                            _allUsers.add(it)
                            Log.e("200", it.picture.thumbnail)
                        }
                    }
                    _state.value = result.data?.let { it1 -> UserState(allUsers = _allUsers) }!!


//                    result.data.let {
//                            if (it != null) {
//                                it.body()?.results?.map {
//                                    _allUsers.add(it)
//
//                                }
//                            }
////                        _state.value =
////                            result.data?.let { it1 -> UserState(allUsers = _allUsers) }!!
//                    }
                }
                is Resource.Error -> {
                    _state.value = UserState(error = result.message.toString())
                }
                is Resource.Loading -> {
                    _state.value = UserState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}