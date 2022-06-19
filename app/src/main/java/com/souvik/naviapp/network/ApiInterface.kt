package com.souvik.naviapp.network

import com.souvik.naviapp.model.RepoModel
import com.souvik.naviapp.constants.Constants
import com.souvik.naviapp.model.PRDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("users/{owner}/repos")
    suspend fun getRepositoryData(
        @Path("owner") owner: String = Constants.REPO_OWNER_NAME
    ): Response<ArrayList<RepoModel?>>

    @GET("repos/{owner}/{repo_name}/pulls")
    suspend fun getClosedPullRequestData(
        @Path("owner") owner: String = Constants.REPO_OWNER_NAME,
        @Path("repo_name") repoName: String,
        @Query("state") state: String = "closed"
    ): Response<ArrayList<PRDataModel?>>
}