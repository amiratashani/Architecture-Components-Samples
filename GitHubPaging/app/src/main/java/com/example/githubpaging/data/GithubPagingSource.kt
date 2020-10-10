package com.example.githubpaging.data

import androidx.paging.PagingSource
import com.example.githubpaging.api.GithubService
import com.example.githubpaging.model.Repo

class GithubPagingSource(
    private val service: GithubService,
    private val query: String
):PagingSource<Int, Repo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        TODO("Not yet implemented")
    }
}