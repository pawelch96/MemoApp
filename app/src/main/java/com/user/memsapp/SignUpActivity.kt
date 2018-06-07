package com.user.memsapp

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    var mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        sign_up_button.setOnClickListener {
            signUp()
        }
    }

    fun signUp () {
        if (isValid(login_text) && isValid(password_text)) {
            if (isEqual(password_text, confirm_password_text)) {
                val email = login_text.text.toString()
                val password = password_text.text.toString()
                signUp(email, password)

            } else {
                password_text.setError("Podane hasła nie są identyczne!")
                confirm_password_text.error = "Podane hasła nie są identyczne!"
            }
        }
    }

    fun isValid (editText : EditText) : Boolean {
        if (editText.text.toString().isEmpty()) {
            editText.setError("To pole jest wymagane!")
            return false
        }
        return true
    }

    fun isEqual (editText : EditText, editText1 : EditText) : Boolean {
        if (editText.text.toString().equals(editText1.text.toString())) {
            return true
        }
        return false
    }

    fun signUp(email : String, password : String) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle(getString(R.string.loading))
        progressDialog.show()
        mAuth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = mAuth!!.currentUser
                        val ref = FirebaseDatabase.getInstance().getReference("users")
                        val name = name_text.text.toString()
                        val newUser = User(user!!.uid, email, name)
                        ref.child(user!!.uid).setValue(newUser).addOnCompleteListener {
                            progressDialog.dismiss()
                            Toast.makeText(applicationContext, " Witaj " + email + "! ", Toast.LENGTH_SHORT).show()
                            val userId = user!!.uid
                            val intent = Intent(this, HomeActivity::class.java)
                            intent.putExtra("email", user!!.email)
                            intent.putExtra("uid", userId)
                            intent.putExtra("name", name)
                            startActivity(intent)
                        }
                    } else {
                        progressDialog.dismiss()
                        Toast.makeText(applicationContext, "Błąd: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}
