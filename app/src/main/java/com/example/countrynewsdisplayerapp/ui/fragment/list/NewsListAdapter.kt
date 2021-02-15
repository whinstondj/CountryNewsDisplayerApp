package com.example.countrynewsdisplayerapp.ui.fragment.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.countrynewsdisplayerapp.R
import com.example.countrynewsdisplayerapp.data.Article
import com.example.countrynewsdisplayerapp.databinding.ItemNewsListBinding

class NewsListAdapter(private var dataSet: List<Article>, private val context: Context, private val listener: (item: Article) -> Unit) : RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemNewsListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemNewsListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet[position]

        var noAuthorOrDescriptionText: String


        viewHolder.binding.apply {
            newsTitleText.text = item.title
            noAuthorOrDescriptionText = when {
                item.author.isNullOrEmpty() -> {
                    context.getString(R.string.noAuthorTextDisplayed)
                }
                else -> {
                    item.author
                }
            }
            newsAuthorText.text = noAuthorOrDescriptionText
            noAuthorOrDescriptionText = when {
                item.description.isNullOrEmpty() -> {
                    context.getString(R.string.noDescriptionTextDisplayed)
                }
                else -> {
                    item.description
                }
            }
            newsDescriptionText.text = noAuthorOrDescriptionText

            Glide.with(context)
                .load(item.urlToImage)
                .centerCrop()
                .placeholder(R.drawable.ic_no_photos)
                .into(newsImagePreview)
        }

        viewHolder.itemView.setOnClickListener {
            listener.invoke(item)
        }
    }

    override fun getItemCount() = dataSet.size

    fun updateList(newList: List<Article>){
        dataSet = newList
        notifyDataSetChanged()
    }
}