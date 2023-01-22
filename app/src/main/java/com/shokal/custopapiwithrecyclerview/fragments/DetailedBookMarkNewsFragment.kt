package com.shokal.custopapiwithrecyclerview.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.shokal.custopapiwithrecyclerview.R
import com.squareup.picasso.Picasso

class DetailedBookMarkNewsFragment : Fragment() {
    private val args: DetailedBookMarkNewsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detailed_book_mark_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = view.findViewById<TextView>(R.id.newTitle)
        val description = view.findViewById<TextView>(R.id.newsDescription)
        val content = view.findViewById<TextView>(R.id.newsContent)
        val imageView = view.findViewById<ImageView>(R.id.newsImage)
        val continueButton = view.findViewById<Button>(R.id.continueButton)
        if (!TextUtils.isEmpty(args.news.title)) {
            title.text = args.news.title
        } else {
            title.text = "No Name"
        }
        if (!TextUtils.isEmpty(args.news.description)) {
            description.text = args.news.description
        } else {
            description.text = "No Description"
        }
        if (!TextUtils.isEmpty(args.news.content)) {
            content.text = args.news.content
        } else {
            content.text = "No Content"
        }
        if (!TextUtils.isEmpty(args.news.content)) {
            Picasso.get()
                .load(args.news.urlToImage)
                .placeholder(R.drawable.ic_connection_error)
                .fit()
                .centerCrop()
                .centerCrop(1)
                .into(imageView)
        } else {
            Picasso.get()
                .load(R.drawable.ic_connection_error)
                .placeholder(R.drawable.ic_connection_error)
                .fit()
                .centerCrop()
                .centerCrop(1)
                .into(imageView)
        }
        continueButton.setOnClickListener {
            if (!TextUtils.isEmpty(args.news.url)) {
                val action =
                    args.news.url?.let {
                        DetailedBookMarkNewsFragmentDirections.actionDetailedBookMarkNewsFragmentToBookMarkWebViewFragment(
                            it
                        )
                    }
                if (action != null) {
                    Navigation.findNavController(view).navigate(action)
                }
            }
        }
    }
}