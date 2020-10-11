package com.example.githubpaging.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubpaging.R
import com.example.githubpaging.model.Repo

class ReposAdapter : PagingDataAdapter<UiModel, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<UiModel>() {
            override fun areItemsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
                return (oldItem is UiModel.RepoItem && newItem is UiModel.RepoItem &&
                        oldItem.repo.fullName == newItem.repo.fullName) ||
                        (oldItem is UiModel.SeparatorItem && newItem is UiModel.SeparatorItem &&
                                oldItem.description == newItem.description)
            }

            override fun areContentsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.repo_view_item) {
            RepoViewHolder.create(parent)
        } else {
            SeparatorViewHolder.create(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val uiModel = getItem(position)
        uiModel.let {
            when (uiModel) {
                is UiModel.RepoItem -> (holder as RepoViewHolder).bind(uiModel.repo)
                is UiModel.SeparatorItem -> (holder as SeparatorViewHolder).bind(uiModel.description)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UiModel.RepoItem -> R.layout.repo_view_item
            is UiModel.SeparatorItem -> R.layout.separator_view_item
            null -> throw UnsupportedOperationException("Unknown view")
        }
    }


    class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.repo_name)
        private val description: TextView = view.findViewById(R.id.repo_description)
        private val stars: TextView = view.findViewById(R.id.repo_stars)
        private val language: TextView = view.findViewById(R.id.repo_language)
        private val forks: TextView = view.findViewById(R.id.repo_forks)
        private var repo: Repo? = null

        init {
            view.setOnClickListener {
                repo?.url?.let { url ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    view.context.startActivity(intent)
                }
            }
        }

        fun bind(repo: Repo?) {
            if (repo == null) {
                val resources = itemView.resources
                name.text = resources.getString(R.string.loading)
                description.visibility = View.GONE
                language.visibility = View.GONE
                stars.text = resources.getString(R.string.unknown)
                forks.text = resources.getString(R.string.unknown)
            } else {
                showRepoData(repo)
            }
        }

        private fun showRepoData(repo: Repo) {
            this.repo = repo
            name.text = repo.fullName

            // if the description is missing, hide the TextView
            var descriptionVisibility = View.GONE
            if (repo.description != null) {
                description.text = repo.description
                descriptionVisibility = View.VISIBLE
            }
            description.visibility = descriptionVisibility

            stars.text = repo.stars.toString()
            forks.text = repo.forks.toString()

            // if the language is missing, hide the label and the value
            var languageVisibility = View.GONE
            if (!repo.language.isNullOrEmpty()) {
                val resources = this.itemView.context.resources
                language.text = resources.getString(R.string.language, repo.language)
                languageVisibility = View.VISIBLE
            }
            language.visibility = languageVisibility
        }


        companion object {
            fun create(parent: ViewGroup): RepoViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.repo_view_item, parent, false)
                return RepoViewHolder(view)
            }
        }
    }

    class SeparatorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val description: TextView = view.findViewById(R.id.separator_description)

        fun bind(separatorText: String) {
            description.text = separatorText
        }

        companion object {
            fun create(parent: ViewGroup): SeparatorViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.separator_view_item, parent, false)
                return SeparatorViewHolder(view)
            }
        }
    }


}