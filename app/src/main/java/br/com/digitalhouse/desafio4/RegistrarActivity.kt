package br.com.digitalhouse.desafio4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_registrar.*


class RegistrarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)

        btnRegistrar.setOnClickListener {
            var email = confirmarEmail.text.toString()
            var password = edSenha.text.toString()
            var rEmailUser = email.isNotEmpty()
            var rPassUser = password.isNotEmpty()

            if (password == senhaConfirmar.text.toString()) {
                if (rPassUser && rEmailUser)
                    registerFirebase(email, password)
                else
                    showMsg("Preencha todos os campos")

            } else {
                showMsg("As senhas ou e-mails estão diferentes.")

            }


        }


    }


    fun callMain(id: String, email: String) {
        var intent = Intent(this, MainActivity::class.java)
        intent.putExtra("user_id", id)
        intent.putExtra("user_email", email)
        startActivity(intent)
        finish()
    }


    fun showMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun registerFirebase(email: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result?.user!!
                    showMsg("Usuário cadastrado!")
                    val idUsuario = firebaseUser.uid
                    val emailUsuario = firebaseUser.email.toString()
                    callMain(idUsuario, emailUsuario)
                } else {
                    showMsg(task.exception?.message.toString())
                }
            }


    }
}