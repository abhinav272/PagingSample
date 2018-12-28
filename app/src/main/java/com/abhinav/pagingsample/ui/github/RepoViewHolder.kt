package com.abhinav.pagingsample.ui.github

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.abhinav.pagingsample.R
import com.abhinav.pagingsample.data.model.RepoEntity

class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val name: TextView = view.findViewById(R.id.repo_name)
    private val description: TextView = view.findViewById(R.id.repo_description)
    private val stars: TextView = view.findViewById(R.id.repo_stars)
    private val language: TextView = view.findViewById(R.id.repo_language)
    private val forks: TextView = view.findViewById(R.id.repo_forks)
    private val deleteBtn: TextView = view.findViewById(R.id.btn_delete)
    private val starBtn: TextView = view.findViewById(R.id.btn_star)

    private var repo: RepoEntity? = null

    companion object {
        private lateinit var listener: (RepoEntity, Int) -> Unit

        fun create(parent: ViewGroup, listener: (RepoEntity, Int) -> Unit): RepoViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.repo_view_item, parent, false)
            this.listener = listener
            return RepoViewHolder(view)
        }
    }

    fun bind(repo: RepoEntity?) {
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

    private fun showRepoData(repo: RepoEntity) {
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

        deleteBtn.setOnClickListener {
            listener(repo, 0)
        }

        starBtn.setOnClickListener { listener(repo, 1) }
    }

}
