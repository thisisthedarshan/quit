/************************************************************************************************************
 *     Copyright (c) 2020. by Darshan. All rights reserved                                                  *
 *                                                                                                          *
 *     The file "Login.kt" is a part of the project "Quit"                                                  *
 *                                                                                                          *
 *     Quit is free software: you can redistribute it and/or modify                                         *
 *     it under the terms of the GNU General Public License as published by                                 *
 *     the Free Software Foundation, either version 3 of the License, or                                    *
 *     (at your option) any later version.                                                                  *
 *                                                                                                          *
 *     Project Quit is distributed in the hope that it will be useful,                                      *
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of                                       *
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                        *
 *     GNU General Public License for more details.                                                         *
 *                                                                                                          *
 *     You should have received a copy of the GNU General Public License                                    *
 *     along with Project Quit.  If not, see <https://www.gnu.org/licenses/>.                               *
 *                                                                                                          *
 ************************************************************************************************************/

package com.aztekstudios.quit.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.aztekstudios.quit.R
import com.aztekstudios.quit.util.C
import com.aztekstudios.quit.util.DataManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

/**
 * Fragment to handle Login for the application.
 */
class Login : Fragment() {
    // Variables
    private lateinit var client: GoogleSignInClient
    private lateinit var gso: GoogleSignInOptions

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get the root view
        val root = inflater.inflate(R.layout.fragment_login, container, false)

        // Get sign-in button
        val signInButton = root.findViewById<SignInButton>(R.id.signIn)

        // Create a GoogleSignInOption
        gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        // Create a client
        client = GoogleSignIn.getClient(requireContext(), gso)

        // Sign-in button action
        signInButton.setOnClickListener {
            val signInIntent: Intent = client.signInIntent
            startActivityForResult(signInIntent, C.SIGN_IN_CODE)
        }

        // Set the size as wide
        signInButton.setSize(SignInButton.SIZE_WIDE)

        // Get the root
        return root
    }

    /**
     * Handle Activity Results here
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Check if the result returned was for Sign-In intent
        if (requestCode == C.SIGN_IN_CODE) {
            // Get the task from sign-in
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                // Try to get account from the sign-in intent
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)!!

                // Store the obtained data from account profile
                val p = DataManager(requireContext())
                val name = account.displayName!!    // Name
                val email = account.email!!         // Email address
                val uid = account.id!!              // A unique ID

                with(p) {
                    // Update the data into database
                    write(C.PROFILE_USER_UID, uid)
                    write(C.PROFILE_NAME, name)
                    write(C.PROFILE_MAIL, email)
                }

                // Change to Profile Creator fragment
                activity?.supportFragmentManager?.commit {
                    replace(R.id.containerProfileMaker, MakeProfile())
                }

            } catch (e: ApiException) {
                // Aw-Snap !
            }
        }
    }
}