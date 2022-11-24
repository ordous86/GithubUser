package com.lucian.githubuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.lucian.githubuser.databinding.UserDetailBinding

/**
 * Fragment for github user detail.
 */
class UserDetailFragment: Fragment() {
    // Fields.
    private val args: UserDetailFragmentArgs by navArgs()
    private val viewModel: UserDetailViewModel by lazy {
        ViewModelProvider(requireActivity(), GithubViewModelFactory())[UserDetailViewModel::class.java]
    }

    // Create view.
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // check safe args
        val login = args.userLogin

        // observe query state
        viewModel.queryState.observe(viewLifecycleOwner) { state ->
            if (state == QueryState.ERROR) {
                Toast.makeText(requireContext(), R.string.load_detail_error, Toast.LENGTH_SHORT).show()
            }
        }

        // observe query strategy
        viewModel.queryStrategy.observe(viewLifecycleOwner) { strategy ->
            when (strategy) {
                QueryStrategy.CACHE -> viewModel.loadCache(login)
                QueryStrategy.DATABASE -> viewModel.loadDatabase(login, GithubDatabase(requireContext()))
                QueryStrategy.ONLINE -> viewModel.loadOnline(login, GithubDatabase(requireContext()))
                else -> throw IllegalArgumentException("Invalid strategy")
            }
        }

        // initialize binding
        return UserDetailBinding.inflate(inflater).also { binding ->
            binding.userLogin = login
            binding.viewModel = viewModel
            binding.lifecycleOwner = this
        }.root
    }

    // Called when view has been successfully created.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // call super
        super.onViewCreated(view, savedInstanceState)

        // trigger query
        viewModel.queryState.value = QueryState.RUNNING
        viewModel.queryStrategy.value = QueryStrategy.CACHE
    }
}