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


class Login : Fragment() {

    lateinit var client: GoogleSignInClient
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_login, container, false)
        val signInButton = root.findViewById<SignInButton>(R.id.signIn)
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        client = GoogleSignIn.getClient(requireContext(), gso)
        signInButton.setOnClickListener {
            val signInIntent: Intent = client.signInIntent
            startActivityForResult(signInIntent, C.SIGN_IN_CODE)
        }
        signInButton.setSize(SignInButton.SIZE_STANDARD)
        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == C.SIGN_IN_CODE) {
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)!!
                // Signed in successfully, show authenticated UI.
                val p = DataManager(requireContext())
                val name = account.displayName!!
                val email = account.email!!
                val uid = account.id!!
                with(p) {
                    write(C.PROFILE_USER_UID, uid)
                    write(C.PROFILE_NAME, name)
                    write(C.PROFILE_MAIL, email)
                }
                activity?.supportFragmentManager?.commit {
                    replace(R.id.containerProfileMaker, MakeProfile())
                } // Change to MakeProfile class
            } catch (e: ApiException) {
                // Aw-Snap !
            }
        }
    }
}