package com.wiryadev.gitplayground.ui.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.wiryadev.gitplayground.data.UserRepository
import com.wiryadev.gitplayground.data.remote.model.UserResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val state: SavedStateHandle
) : ViewModel() {

    private val username = state.get<String>("username").orEmpty()

    val pagingFollow = UserRepository.getAllFollowerPaging(username).cachedIn(viewModelScope)

    val user: MutableStateFlow<UserResponse?> = MutableStateFlow(null)
    val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            isLoading.emit(true)
            try {
                user.emit(UserRepository.getUser(username))
            } catch (e: Exception) {
                Log.d("DetailViewModel", "searchUser: $e")
            } finally {
                isLoading.emit(false)
            }
        }
    }

}