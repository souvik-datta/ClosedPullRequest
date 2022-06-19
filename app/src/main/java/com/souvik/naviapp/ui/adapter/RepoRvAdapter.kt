package com.souvik.naviapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.souvik.naviapp.model.RepoModel
import com.souvik.naviapp.databinding.RepoRvLayoutBinding

class RepoRvAdapter(private val list: ArrayList<RepoModel?>, val listener: OnItemClickListener):RecyclerView.Adapter<RepoRvAdapter.RepoViewHolder>() {

    inner class RepoViewHolder(private val binding: RepoRvLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: RepoModel?){
            Glide.with(binding.ivUserImage.context)
                .load(data?.owner?.avatar_url)
                .into(binding.ivUserImage)
            binding.tvRepoTitle.text = "Name: ${data?.name}"
            binding.tvRepoOwner.text = "Owner: ${data?.owner?.login}"
            binding.tvRepoLanguage.text = "Language: ${data?.language}"
            binding.rvContainer.setOnClickListener {
                listener.onClick(data)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepoViewHolder {
        val binding = RepoRvLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(data = list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener{
        fun onClick(data: RepoModel?)
    }
}