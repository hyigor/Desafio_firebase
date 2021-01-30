package br.com.digitalhouse.desafio4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_jogo_descricao.*
import kotlinx.android.synthetic.main.frag_jogo.*

class JogoDescricaoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogo_descricao)

        btnVoltar.setOnClickListener{
            finish()
        }

        btnEditarJogo.setOnClickListener{
            val name = intent.getSerializableExtra("name") as? String
            val data = intent.getSerializableExtra("lancamento") as? String
            val description = intent.getSerializableExtra("descricao") as? String
            val URL = intent.getSerializableExtra("url") as? String
            val key = intent.getSerializableExtra("key") as? String


            val intent = Intent(this, RegistrarJogoActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("lancamento", data)
            intent.putExtra("descricao", description)
            intent.putExtra("url", URL)
            intent.putExtra("key", key)
            intent.putExtra("edit", true)
            startActivity(intent)
        }

        val name = intent.getSerializableExtra("name") as? String
        val data = intent.getSerializableExtra("lancamento") as? String
        val description = intent.getSerializableExtra("descricao") as? String
        val URL = intent.getSerializableExtra("url") as? String
        val key = intent.getSerializableExtra("key") as? String

        Picasso.get().load(URL).into(imgCard)
        tituloJogo.text = name
        titJogo.text = name
        dataJogo.text = "Lançamento: " + data
        descJogo.text = description




    }
}