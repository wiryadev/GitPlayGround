package com.wiryadev.gitplayground.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.wiryadev.gitplayground.data.remote.model.UserResponse

val USER_COMPARATOR = object : DiffUtil.ItemCallback<UserResponse>() {

    override fun areItemsTheSame(
        oldItem: UserResponse,
        newItem: UserResponse
    ): Boolean = oldItem.id == newItem.id
            && oldItem.login == newItem.login

    override fun areContentsTheSame(
        oldItem: UserResponse,
        newItem: UserResponse
    ): Boolean = oldItem == newItem
}