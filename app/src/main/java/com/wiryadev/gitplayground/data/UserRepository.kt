package com.wiryadev.gitplayground.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.wiryadev.gitplayground.data.remote.ApiClient
import com.wiryadev.gitplayground.data.remote.model.UserResponse
import kotlinx.coroutines.flow.Flow

object UserRepository {

    suspend fun searchUser(query: String) = ApiClient.service.searchUser(query).items

    suspend fun getUser(username: String) = ApiClient.service.getUser(username)

    fun getAllFollowerPaging(name: String): Flow<PagingData<UserResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                UserPagingSource { page, size ->
                    ApiClient.service.getFollowers(name, page, size)
                }
            }
        ).flow
    }

}