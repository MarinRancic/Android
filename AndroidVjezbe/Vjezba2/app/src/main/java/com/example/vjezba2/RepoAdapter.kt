package com.example.vjezba2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

data class Repo(val name: String, val stars: Int, val avatarUrl: String)

class RepoAdapter(private val repoList: List<Repo>) :
    RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar: ImageView = itemView.findViewById(R.id.avatarImageView)
        val name: TextView = itemView.findViewById(R.id.repoNameTextView)
        val stars: TextView = itemView.findViewById(R.id.starsTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.repo_item, parent, false)
        return RepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = repoList[position]
        holder.name.text = repo.name
        holder.stars.text = "Stars: ${repo.stars}"
        Glide.with(holder.avatar.context).load(repo.avatarUrl).into(holder.avatar)
    }

    override fun getItemCount(): Int = repoList.size
}
