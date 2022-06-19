package com.souvik.naviapp.network

import com.souvik.naviapp.model.PRDataModel
import com.souvik.naviapp.model.RepoModel

class Repository private constructor() {
    companion object {
        private lateinit var INSTANCE: Repository
        fun getInstance(): Repository {
            if (this::INSTANCE.isInitialized)
                return INSTANCE
            else {
                INSTANCE = Repository()
                return INSTANCE
            }
        }
    }

    suspend fun getUserRepoDetails(): ArrayList<RepoModel?>? {
        return ApiClient.apiInterface?.getRepositoryData()?.body()
    }

    suspend fun getClosedPR(repoName: String): ArrayList<PRDataModel?>? {
        return ApiClient.apiInterface?.getClosedPullRequestData(repoName = repoName)?.body()
    }
}
