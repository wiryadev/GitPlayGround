package com.wiryadev.gitplayground.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wiryadev.gitplayground.data.remote.model.UserResponse
import retrofit2.HttpException
import java.io.IOException

class UserPagingSource(
    private val usersFactory: suspend (Int, Int) -> List<UserResponse>
) : PagingSource<Int, UserResponse>() {

    override fun getRefreshKey(state: PagingState<Int, UserResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserResponse> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        return try {
            val response = usersFactory.invoke(position, params.loadSize)
            val nextKey = if (response.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = response,
                prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}

const val NETWORK_PAGE_SIZE = 20
private const val GITHUB_STARTING_PAGE_INDEX = 1