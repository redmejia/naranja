package com.bitinovus.naranja.data.remote.page

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bitinovus.naranja.data.remote.api.OrangeAPI
import com.bitinovus.naranja.data.remote.model.Profile

// use with LazyColumn
class ProfilePagingSource(
    private val api: OrangeAPI,
    private val startPage: Int,
) : PagingSource<Int, Profile>() {
    override fun getRefreshKey(state: PagingState<Int, Profile>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Profile> {

        return try {
            val currentPage = params.key ?: startPage
            val response = api.getProfiles(page = currentPage, limit = 3)

            if (response.isSuccessful) {
                val profiles = response.body()?.data ?: emptyList()
                LoadResult.Page(
                    data = profiles,
                    prevKey = if (currentPage == 1) null else currentPage.minus(1),
                    nextKey = if (profiles.isEmpty()) null else currentPage.plus(1)
                )
            } else {
                LoadResult.Error(Exception("Failed to load data"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }
}