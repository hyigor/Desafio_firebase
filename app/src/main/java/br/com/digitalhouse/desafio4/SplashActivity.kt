package br.com.digitalhouse.desafio4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashActivity : AppCompatActivity() {
    val scope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashCoroutine()
    }

    fun splashCoroutine(){
        val intent = Intent(this, LoginActivity::class.java)
        scope.launch {
            delay(2000)
            startActivity(intent)
            finish()
        }
    }
}
