package com.souvik.naviapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.souvik.naviapp.databinding.PrRvLayoutBinding
import com.souvik.naviapp.model.PRDataModel

class PrRVAdapter(val list: ArrayList<PRDataModel?>) :
    RecyclerView.Adapter<PrRVAdapter.PRViewHolder>() {
    class PRViewHolder(val binding: PrRvLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PRDataModel?) {
            Glide.with(binding.ivUserImage.context)
                .load(data?.user?.avatar_url)
                .into(binding.ivUserImage)
            binding.tvTitle.text = "Title: ${data?.title}"
            binding.tvUserName.text = data?.user?.login
            binding.tvPrOpen.text = "PR Created At: ${data?.created_at}"
            binding.tvPrClose.text = "PR Closed At: ${data?.closed_at}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PRViewHolder {
        val binding = PrRvLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PRViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PRViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}