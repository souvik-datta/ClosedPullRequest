package com.souvik.naviapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.souvik.naviapp.R
import com.souvik.naviapp.databinding.FragmentPrInfoBinding
import com.souvik.naviapp.model.PRDataModel
import com.souvik.naviapp.model.RepoModel
import com.souvik.naviapp.ui.adapter.PrRVAdapter
import com.souvik.naviapp.ui.adapter.RepoRvAdapter
import com.souvik.naviapp.viewmodel.GitHubViewModel

class PrInfoFragment private constructor() : Fragment() {

    private lateinit var binding: FragmentPrInfoBinding
    private lateinit var viewModel: GitHubViewModel
    private lateinit var list: ArrayList<PRDataModel?>
    private lateinit var rvAdapter: PrRVAdapter


    companion object {
        private const val REPO_NAME = "repo_name"

        fun newInstance(repoName: String): PrInfoFragment {
            return PrInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(REPO_NAME, repoName)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pr_info, container, false)
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
        setUpRVAdapter()
        binding.progressBar.visibility = View.VISIBLE
        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.rvPr.addItemDecoration(itemDecorator)
    }

    private fun setUpRVAdapter() {
        rvAdapter = PrRVAdapter(list)
        binding.rvPr.adapter = rvAdapter
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this).get(GitHubViewModel::class.java)
        arguments?.getString(REPO_NAME)?.let { viewModel.getAllClosedPR(it) }
    }

    private fun setUpObserver() {
        viewModel.prList.observe(viewLifecycleOwner, object : Observer<ArrayList<PRDataModel?>> {
            override fun onChanged(t: ArrayList<PRDataModel?>?) {
                binding.progressBar.visibility = View.GONE
                if (t?.size ?: 0 == 0) {
                    Snackbar.make(binding.root, "No data present", Snackbar.LENGTH_LONG)
                        .show()
                    return
                }
                list.clear()
                if (t != null) {
                    list.addAll(t)
                    rvAdapter.notifyDataSetChanged()
                }
            }
        })
    }
}