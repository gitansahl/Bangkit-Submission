package com.gitan.mystoryapp.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gitan.mystoryapp.data.response.GetAllStoriesResponse
import com.gitan.mystoryapp.data.response.ListStoryItem
import com.gitan.mystoryapp.data.retrofit.ApiService
import java.lang.Exception

class StoryPagingSource(private val apiService: ApiService, private val token: String): PagingSource<Int, ListStoryItem>() {
    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            Log.d("StoryPagingSource", "Loading on Story Paging Source")
            val position = params.key ?: INITIAL_PAGE_INDEX
            Log.d("StoryPagingSource", "$position on request")
            Log.d("StoryPagingSource", "${params.loadSize} on request loadsize")
            val responseData = apiService.getAllStories("Bearer $token", position, params.loadSize).listStory

            Log.d("StoryPagingSource", responseData.toString())
            LoadResult.Page(
                data = responseData,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.isNullOrEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}