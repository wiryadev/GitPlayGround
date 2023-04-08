package com.wiryadev.gitplayground.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.wiryadev.gitplayground.data.remote.model.UserResponse
import com.wiryadev.gitplayground.databinding.ActivityDetailBinding
import com.wiryadev.gitplayground.ui.adapter.UserLoadStateAdapter
import com.wiryadev.gitplayground.ui.adapter.UserPagingAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val viewModel: DetailViewModel by viewModels()
    private val pagingAdapter by lazy {
        UserPagingAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.layoutToolbar)

        binding.rvFollower.apply {
            layoutManager = LinearLayoutManager(this@DetailActivity)
            adapter = pagingAdapter.withLoadStateFooter(
                footer = UserLoadStateAdapter {
                    pagingAdapter.retry()
                }
            )
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.isLoading.collect {
                        binding.pbLoading.isVisible = it
                    }
                }
                launch {
                    viewModel.user.collect {
                        if (it != null) {
                            setDetailUser(it)
                        }
                    }
                }
                launch {
                    viewModel.pagingFollow.collect {
                        pagingAdapter.submitData(it)
                    }
                }
            }
        }
    }

    private fun setDetailUser(user: UserResponse) {
        with(binding) {
            supportActionBar?.title = user.login
            ivAvatar.load(user.avatarUrl)
            tvName.text = user.name
            tvBio.text = user.bio
            tvLocation.text = user.location
            tvCompany.text = user.company
            tvFollower.text = user.followers.toString()
            tvFollowing.text = user.following.toString()
            tvRepos.text = user.publicRepos.toString()
            tvGists.text = user.publicGists.toString()
        }
    }
}