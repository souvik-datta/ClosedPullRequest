package com.souvik.naviapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.souvik.naviapp.viewmodel.GitHubViewModel
import com.souvik.naviapp.R
import com.souvik.naviapp.model.RepoModel
import com.souvik.naviapp.databinding.FragmentRepoBinding
import com.souvik.naviapp.ui.adapter.RepoRvAdapter


class RepoFragment() : Fragment() {

    private lateinit var binding: FragmentRepoBinding
    private lateinit var viewModel: GitHubViewModel
    private lateinit var list: ArrayList<RepoModel?>
    private lateinit var rvAdapter: RepoRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_repo, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = ArrayList()
        initUI()
        setUpViewModel()
        setUpObserver()
    }

    private fun initUI() {
        binding.progressBar.visibility = View.VISIBLE
        setUpRcAdapter()
        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.rvRepo.addItemDecoration(itemDecorator)
    }

    private fun setUpRcAdapter() {
        rvAdapter = RepoRvAdapter(list, object : RepoRvAdapter.OnItemClickListener {
            override fun onClick(data: RepoModel?) {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container, PrInfoFragment.newInstance(data?.name ?: ""))
                    .addToBackStack("PRFragment")
                    .commit()
            }
        })
        binding.rvRepo.adapter = rvAdapter
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this).get(GitHubViewModel::class.java)
    }

    private fun setUpObserver() {
        viewModel.getAllPublicRepos()
        viewModel.repoList.observe(viewLifecycleOwner) {
            Log.d("TAG", "onViewCreated: $it")
            binding.progressBar.visibility = View.GONE
            if (it.size == 0) {
                Snackbar.make(binding.root, "No data present", Snackbar.LENGTH_LONG).show()
                return@observe
            }
            list.clear()
            list.addAll(it)
            rvAdapter.notifyDataSetChanged()
        }
    }
}