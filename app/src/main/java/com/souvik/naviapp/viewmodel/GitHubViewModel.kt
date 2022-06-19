package com.souvik.naviapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.souvik.naviapp.model.PRDataModel
import com.souvik.naviapp.model.RepoModel
import com.souvik.naviapp.network.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GitHubViewModel : ViewModel() {

    private var _repoList = MutableLiveData<ArrayList<RepoModel?>>()
    val repoList: LiveData<ArrayList<RepoModel?>>
        get() {
            return _repoList
        }

    private var _prList = MutableLiveData<ArrayList<PRDataModel?>>()
    val prList: LiveData<ArrayList<PRDataModel?>>
        get() {
            return _prList
        }

    fun getAllPublicRepos() {
        viewModelScope.launch(Dispatchers.IO) {
            _repoList.postValue(Repository.getInstance().getUserRepoDetails())
        }
    }

    fun getAllClosedPR(repoName:String) {
        viewModelScope.launch(Dispatchers.IO) {
            _prList.postValue(Repository.getInstance().getClosedPR(repoName))
        }
    }
}