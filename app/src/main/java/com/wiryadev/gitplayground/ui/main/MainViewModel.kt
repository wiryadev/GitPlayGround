package com.wiryadev.gitplayground.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiryadev.gitplayground.data.UserRepository
import com.wiryadev.gitplayground.data.remote.model.UserResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val users: MutableStateFlow<List<UserResponse>> = MutableStateFlow(emptyList())
    val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    fun searchUser(query: String) {
        viewModelScope.launch {
            isLoading.emit(true)
            try {
                users.emit(UserRepository.searchUser(query))
            } catch (e: Exception) {
                Log.d("MainViewModel", "searchUser: $e")
            } finally {
                isLoading.emit(false)
            }
        }
    }
}