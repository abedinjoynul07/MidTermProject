package com.shokal.custopapiwithrecyclerview.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.constraintlayout.utils.widget.ImageFilterButton
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.shokal.custopapiwithrecyclerview.R
import com.shokal.custopapiwithrecyclerview.fragments.HomeFragmentDirections
import com.shokal.custopapiwithrecyclerview.models.BookMarkNews
import com.shokal.custopapiwithrecyclerview.models.LocalArticle
import com.shokal.custopapiwithrecyclerview.viewmodels.LocalNewsViewModel
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class NewsAdapter(
    private val context: Context,
    private val viewModel: LocalNewsViewModel,
    private val list: List<LocalArticle>
) : RecyclerView.Adapter<NewsAdapter.ItemViewHolder>() {
    private var tasksList = list

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imageView)
        val title: TextView = view.findViewById(R.id.newsTitle)
        val description: TextView = view.findViewById(R.id.newsDescription)
        val authorName: TextView = view.findViewById(R.id.authorName)
        val date: TextView = view.findViewById(R.id.date)
        val newsCard: CardView = view.findViewById(R.id.cardViewNews)
        val favButton: ImageFilterButton = view.findViewById(R.id.favouriteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ItemViewHolder(root)
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val news = tasksList[position]

        if (!TextUtils.isEmpty(news.title)) {
            holder.title.text = news.title
        } else {
            holder.title.text = "No Title!"
        }
        if (!TextUtils.isEmpty(news.description)) {
            holder.description.text = news.description
        } else {
            holder.description.text = "No Description!"
        }
        if (!TextUtils.isEmpty(news.author)) {
            holder.authorName.text = news.author
        } else {
            holder.authorName.text = "No Author!"
        }
        if (!TextUtils.isEmpty(news.publishedAt)) {
            holder.date.text = news.publishedAt?.removeRange(10..19) ?: "No Date"
        } else {
            holder.date.text = "No Date"
        }
        if (!TextUtils.isEmpty(news.urlToImage)) {
            Picasso.get().load(news.urlToImage).fit().placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_connection_error).centerCrop(1).centerCrop().into(holder.image)
        } else {
            Picasso.get().load(R.drawable.ic_connection_error).fit()
                .placeholder(R.drawable.loading_animation).error(R.drawable.ic_connection_error)
                .centerCrop(1).centerCrop().into(holder.image)
        }

        if (news.isFavourite) {
            holder.favButton.setImageResource(R.drawable.baseline_bookmark_24)
        } else {
            holder.favButton.setImageResource(R.drawable.baseline_bookmark_border_24)
        }

        holder.favButton.setOnClickListener {
            val article = LocalArticle(
                news.author,
                news.content,
                news.description,
                news.publishedAt,
                news.title,
                news.url,
                news.urlToImage,
                news.category,
                true
            )
            viewModel.updateArticle(article)

            val bookmarkNews = BookMarkNews(
                news.author,
                news.content,
                news.description,
                news.publishedAt,
                news.title,
                news.url,
                news.urlToImage
            )
            viewModel.addBookMarkArticle(bookmarkNews)
        }
        holder.newsCard.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragment2ToDetailedNewsFragment(news)
            Navigation.findNavController(holder.itemView).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return tasksList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filterlist: List<LocalArticle>) {
        tasksList = filterlist
        notifyDataSetChanged()
    }

    fun filter(text: String) {
        val filteredList = ArrayList<LocalArticle>()
        for (article in list) {
            if (article.title?.lowercase(Locale.ROOT)
                    ?.contains(text.lowercase(Locale.ROOT)) == true
            ) {
                filteredList.add(article)
            }
        }
        filterList(filteredList)
    }
}