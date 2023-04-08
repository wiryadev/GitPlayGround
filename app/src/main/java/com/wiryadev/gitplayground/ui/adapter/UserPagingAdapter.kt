package com.wiryadev.gitplayground.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wiryadev.gitplayground.R
import com.wiryadev.gitplayground.data.remote.model.UserResponse
import com.wiryadev.gitplayground.databinding.ItemUserBinding
import com.wiryadev.gitplayground.databinding.PagingLoadStateFooterBinding

class UserPagingAdapter : PagingDataAdapter<UserResponse, UserViewHolder>(USER_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return UserViewHolder(binding) {}
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}

class UserLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<UserLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: UserLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): UserLoadStateViewHolder {
        return UserLoadStateViewHolder.create(parent, retry)
    }
}

class UserLoadStateViewHolder(
    private val binding: PagingLoadStateFooterBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
        binding.errorMsg.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): UserLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.paging_load_state_footer, parent, false)
            val binding = PagingLoadStateFooterBinding.bind(view)
            return UserLoadStateViewHolder(binding, retry)
        }
    }
}