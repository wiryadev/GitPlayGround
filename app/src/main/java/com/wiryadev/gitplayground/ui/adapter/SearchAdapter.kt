package com.wiryadev.gitplayground.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.wiryadev.gitplayground.data.remote.model.UserResponse
import com.wiryadev.gitplayground.databinding.ItemUserBinding

class SearchAdapter(
    private val onClick: (UserResponse) -> Unit,
) : ListAdapter<UserResponse, UserViewHolder>(USER_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return UserViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}

