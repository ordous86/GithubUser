package com.lucian.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.addCallback
import androidx.navigation.findNavController
import com.lucian.githubuser.databinding.UserListBinding

/**
 * Activity to show github user information.
 */
class GithubActivity: AppCompatActivity() {
    // Fields.
    var userListBinding: UserListBinding? = null

    // Create.
    override fun onCreate(savedInstanceState: Bundle?) {
        // call super
        super.onCreate(savedInstanceState)

        // initialize layout
        setContentView(R.layout.activity_main)

        // handle back key event
        onBackPressedDispatcher.addCallback(this) {
            findNavController(R.id.nav_host_fragment).also {
                if (it.currentDestination?.label == "UserDetailFragment") {
                    it.popBackStack()
                } else {
                    finish()
                }
            }
        }
    }
}