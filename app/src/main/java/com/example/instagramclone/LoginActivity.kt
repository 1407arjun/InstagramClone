package com.example.instagramclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        loginEmailEditText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginButton.isEnabled = !(s.isNullOrEmpty() and loginPasswordEditText.text.isNullOrEmpty())
            }

            override fun afterTextChanged(s: Editable?) {}
        });

        loginPasswordEditText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginButton.isEnabled = !(s.isNullOrEmpty() and loginEmailEditText.text.isNullOrEmpty())
            }

            override fun afterTextChanged(s: Editable?) {}
        });

        val email: String = loginEmailEditText.text.toString()
        val password: String = loginPasswordEditText.text.toString()
        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

        loginButton.setOnClickListener {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{task ->
                    if (task.isSuccessful){
                        val intent: Intent = Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                    }
                }.addOnFailureListener{exception ->
                    var message: String = ""
                    message = when (exception) {
                        is FirebaseAuthInvalidCredentialsException -> "Incorrect password for $email"
                        is FirebaseNetworkException -> "Unknown network error"
                        else -> "Unknown error occurred. Please try again"
                    }
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }

    }
}