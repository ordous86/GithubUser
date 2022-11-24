package com.lucian.githubuser

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

/**
 * Interface for layout binding functions.
 */
interface Bindings

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, url: String?) {
    val target = url ?: R.drawable.ic_launcher_foreground
    Glide.with(imageView.context)
        .load(target)
        .placeholder(R.drawable.ic_launcher_foreground)
        .into(imageView)
}

@BindingAdapter("layoutManager")
fun setLayoutManager(recyclerView: RecyclerView, layoutManager: LinearLayoutManager) {
    recyclerView.layoutManager = layoutManager
    recyclerView.addItemDecoration(DividerItemDecoration(
        recyclerView.context, layoutManager.orientation)
    )
}

@BindingAdapter("userListAdapter")
fun setUserListAdapter(recyclerView: RecyclerView, adapter: UserListAdapter) {
    recyclerView.adapter = adapter
}