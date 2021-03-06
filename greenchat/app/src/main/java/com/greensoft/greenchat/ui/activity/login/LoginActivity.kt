package com.greensoft.greenchat.ui.activity.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.greensoft.greenchat.R
import com.greensoft.greenchat.ui.activity.message.MessagesActivity
import com.greensoft.greenchat.ui.activity.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.edPassword


class LoginActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btnLogin -> {
                val email = edEmail.text.toString()
                val password = edPassword.text.toString()

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(applicationContext, "email/password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                    return
                }

                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(applicationContext, MessagesActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            Toast.makeText(applicationContext, "Succes Login ${it.result?.user?.email} ", Toast.LENGTH_SHORT).show()
                            return@addOnCompleteListener
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(applicationContext, "${it.message}", Toast.LENGTH_SHORT).show()
                        edEmail.text.clear()
                        edPassword.text.clear()
                    }
            }
        }
    }
}