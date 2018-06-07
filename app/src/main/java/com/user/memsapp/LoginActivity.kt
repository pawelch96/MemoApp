package com.user.memsapp

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    var mAuth = FirebaseAuth.getInstance()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        login_button.setOnClickListener {
            logIn()
        }

        sign_up_button.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }

    fun isValid (editText: EditText) : Boolean {
        if (editText.text.toString().isEmpty()){
            editText.error ="Pole nie może być puste!"
            return false
        }
        return true
    }

    fun logIn () {
        if (isValid(login_text) && isValid(password_text)) {
            var email: String = login_text.text.toString()
            var password: String = password_text.text.toString()
            signIn(email, password)
        }
    }

    fun signIn (email : String, password : String) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle(getString(R.string.loading))
        progressDialog.show()
        sign_up_button.isEnabled.not()

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
            if (task.isSuccessful) {
                val user = mAuth!!.currentUser
                progressDialog.dismiss()
                Toast.makeText(applicationContext, " Witaj " + email + "! ", Toast.LENGTH_SHORT).show()
                val userId = user!!.uid
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("uid", userId)
                intent.putExtra("email", email)

                startActivity(intent)

            } else {
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "Błąd: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                sign_up_button.isEnabled
            }
        })
    }
}
