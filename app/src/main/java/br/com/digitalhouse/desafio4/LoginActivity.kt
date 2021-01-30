package br.com.digitalhouse.desafio4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

private lateinit var mAuth: FirebaseAuth


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val user = FirebaseAuth.getInstance().currentUser
        if(user != null){
            callMain(user.uid, user.email.toString())
        }

        btnLogin.setOnClickListener {
            signIn()
        }

        btnRegister.setOnClickListener{
            callRegistrar()
        }



    }

    fun callMain(idUsario:String, emailUsuario: String){
        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra("id", idUsario)
        intent.putExtra("email", emailUsuario)
        startActivity(intent)
    }

    fun callRegistrar(){
        val intent = Intent(this,RegistrarActivity::class.java)
        startActivity(intent)
    }
    fun showMsg(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    fun signIn(){
        val email = edEmailLogin.text.toString()
        val password = edSenhaLogin.text.toString()
        val emailEmp = email.isNotBlank()
        val passwordEmp = password.isNotBlank()
        if(emailEmp && passwordEmp) {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser
                        callMain(email, password)
                    } else {
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }

                }
        } else {
            showMsg("Preencha todas as informações.")

        }
    }

}