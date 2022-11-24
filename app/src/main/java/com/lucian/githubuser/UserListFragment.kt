package com.lucian.githubuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.lucian.githubuser.databinding.UserListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Fragment for github user list.
 */
class UserListFragment: Fragment() {
    // Fields.
    private val viewModel: UserListViewModel by lazy {
        ViewModelProvider(requireActivity(), GithubViewModelFactory())[UserListViewModel::class.java]
    }

    // Define interface for item click event.
    interface ListItemClickListener {
        fun onItemClick(login: String)
    }

    // Create view.
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // check activity
        val activity = this.activity.let {
            if (it is GithubActivity)
                it
            else
                return super.onCreateView(inflater, container, savedInstanceState)
        }

        // check binding
        return activity.userListBinding?.root ?: let {
            // check adapter
            val adapter = UserListAdapter(this, object: ListItemClickListener {
                override fun onItemClick(login: String) {
                    findNavController().navigate(UserListFragmentDirections.actionToDetail(login))
                }
            })

            // check pager
            val pagerLiveData = Pager(PagingConfig(PAGE_SIZE, enablePlaceholders = false),
                pagingSourceFactory = { viewModel.dataSource }).liveData

            // observe pager
            pagerLiveData.observe(viewLifecycleOwner) { userList ->
                viewModel.viewModelScope.launch(Dispatchers.Main + viewModel.coroutineExceptionHandler) {
                    adapter.submitData(userList)
                }
            }

            // observe query state
            viewModel.queryState.observe(viewLifecycleOwner) { state ->
                if (state == QueryState.ERROR) {
                    Toast.makeText(requireContext(), R.string.load_list_error, Toast.LENGTH_SHORT).show()
                }
            }

            // initialize binding
            UserListBinding.inflate(inflater).also { binding ->
                binding.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.userListAdapter = adapter
                binding.lifecycleOwner = this
                activity.userListBinding = binding
            }.root
        }
    }
}