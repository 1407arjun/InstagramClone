package com.example.instagramclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

        loginEmailEditText.isEnabled = true
        loginPasswordEditText.isEnabled = true
        loginButton.isEnabled = false
        //animationView.layoutParams.height = loginButton.layoutParams.height
        animationView.visibility = View.GONE
        
        loginEmailEditText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginButton.isEnabled = !(s.isNullOrEmpty() or loginPasswordEditText.text.isNullOrEmpty())
            }

            override fun afterTextChanged(s: Editable?) {}
        });

        loginPasswordEditText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginButton.isEnabled = !(s.isNullOrEmpty() or loginEmailEditText.text.isNullOrEmpty())
            }

            override fun afterTextChanged(s: Editable?) {}
        });

        loginButton.setOnClickListener {
            loginButton.text = ""
            animationView.playAnimation()
            loginButton.backgroundTintList = getColorStateList(R.color.load_button_bg_selector)
            animationView.visibility = View.VISIBLE
            loginButton.isEnabled = false
            val email: String = loginEmailEditText.text.toString()
            val password: String = loginPasswordEditText.text.toString()

            loginEmailEditText.isEnabled = false
            loginPasswordEditText.isEnabled = false

            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{task ->
                    if (task.isSuccessful){
                        animationView.visibility = View.GONE
                        loginButton.backgroundTintList = getColorStateList(R.color.button_bg_selector)
                        loginButton.text = getString(R.string.login_text)
                        val intent: Intent = Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                        finish()
                    }
                }.addOnFailureListener{exception ->
                    loginEmailEditText.isEnabled = true
                    loginPasswordEditText.isEnabled = true
                    animationView.visibility = View.GONE
                    loginButton.backgroundTintList = getColorStateList(R.color.button_bg_selector)
                    loginButton.text = getString(R.string.login_text)
                    loginButton.isEnabled = true
                    val message = when (exception) {
                        is FirebaseAuthInvalidCredentialsException -> "Incorrect password for $email"
                        is FirebaseNetworkException -> "Unknown network error"
                        else -> "Unknown error occurred. Please try again"
                    }
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }

        registerTextView.setOnClickListener{
            val intent: Intent = Intent(this, RegisterActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

    }
}