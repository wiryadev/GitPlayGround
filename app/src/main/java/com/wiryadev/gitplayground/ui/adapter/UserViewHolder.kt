package com.wiryadev.gitplayground.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.wiryadev.gitplayground.data.remote.model.UserResponse
import com.wiryadev.gitplayground.databinding.ItemUserBinding

class UserViewHolder(
    private val binding: ItemUserBinding,
    private val onClick: (UserResponse) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: UserResponse) = with(binding) {
        ivAvatar.load(user.avatarUrl)
        tvUsername.text = user.login
        root.setOnClickListener { onClick(user) }
    }
}