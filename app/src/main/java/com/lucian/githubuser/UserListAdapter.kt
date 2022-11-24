package com.lucian.githubuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lucian.githubuser.databinding.UserListItemBinding
import com.lucian.githubuser.UserListAdapter.UserViewHolder
import com.lucian.githubuser.UserListFragment.ListItemClickListener
import com.lucian.githubuser.UserListRepository.User

/**
 * Adapter for Github user list.
 */
class UserListAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val listener: ListItemClickListener): PagingDataAdapter<User, UserViewHolder>(DiffCallback) {

    // Callback for calculating the diff between 2 list items.
    companion object DiffCallback: DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User) = (oldItem === newItem)
        override fun areContentsTheSame(oldItem: User, newItem: User) =
                (oldItem.avatarUrl == newItem.avatarUrl) &&
                (oldItem.id == newItem.id) &&
                (oldItem.isAdmin == newItem.isAdmin) &&
                (oldItem.login == newItem.login)
    }

    // Define view holder for adapter.
    inner class UserViewHolder(private val binding: UserListItemBinding): RecyclerView.ViewHolder(binding.root) {
        // Set data to list item.
        fun setData(user: User) {
            binding.user = user
            binding.executePendingBindings()
        }
    }

    // Bind.
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.also {
            holder.setData(it)
        }
    }

    // Create.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        // initialize binding
        UserListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).also {
            // initialize binding variables
            it.listItemClickListener = listener
            it.lifecycleOwner = lifecycleOwner

            // initialize view holder
            return UserViewHolder(it)
        }
    }
}