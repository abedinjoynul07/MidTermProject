package com.shokal.custopapiwithrecyclerview.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.shokal.custopapiwithrecyclerview.R
import com.shokal.custopapiwithrecyclerview.models.Article
import com.shokal.custopapiwithrecyclerview.viewmodels.NewsViewModel
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NewsAdapter(
    private val context: Context,
    val viewModel: NewsViewModel,
    private val arrayList: ArrayList<Article>
): RecyclerView.Adapter<NewsAdapter.ItemViewHolder>() {
    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.imageView)
        val title = view.findViewById<TextView>(R.id.newsTitle)
        val description = view.findViewById<TextView>(R.id.newsDescription)
        val autorName = view.findViewById<TextView>(R.id.authorName)
        val date = view.findViewById<TextView>(R.id.date)
        val newsCard = view.findViewById<CardView>(R.id.cardViewNews)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ItemViewHolder(root)
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val news = arrayList[position]

        holder.title.text = news.title!!
        holder.description.text = news.description!!
        holder.autorName.text = news.author!!
        val localDate = LocalDate.parse("01-06-2022", DateTimeFormatter.ofPattern("MM-dd-yyyy"))
        if (localDate != null) {
            holder.date.text = localDate.toString()
        }
        Picasso.get()
            .load(news.urlToImage)
            .fit()
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_connection_error)
            .centerCrop(1)
            .centerCrop()
            .into(holder.image)


        holder.newsCard.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}