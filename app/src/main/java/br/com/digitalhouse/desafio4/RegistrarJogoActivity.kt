package br.com.digitalhouse.desafio4

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_registrar_jogo.*

import dmax.dialog.SpotsDialog

class RegistrarJogoActivity : AppCompatActivity() {
    val sharedPrefs by lazy {  getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }
    val PREFS_NAME = "user_prefs"

    lateinit var alertDialog: AlertDialog
    lateinit var storageReference: StorageReference
    var URL: String = ""
    private var ImageSet = false
    private val service = Repositorio()
    private val CODE_IMG = 1000
    val KEY_EMAIL = "prefs.email"
    val EMPTY_STRING = ""
    private val COD_IMG = 1000



    private val viewModelGame by viewModels<MainViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(service) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_jogo)

        btnSalvarJogo.setOnClickListener {
                var id = getUniqueId()
                if(ImageSet == true){
                    viewModelGame.enviarJogo(Jogo(id, addNameJogo.text.toString(), criadoEm.text.toString(), colocarDesc.text.toString(), URL))
                    callMain()
                }
                val edit = intent.getSerializableExtra("edit") as? Boolean
                val key = intent.getSerializableExtra("key") as? String
                val UrlKey = intent.getSerializableExtra("url") as? String

                Log.i("edit", edit.toString())
                if(edit == true){
                    viewModelGame.AtuaJogos(Jogo(key.toString(), addNameJogo.text.toString(), criadoEm.text.toString(), colocarDesc.text.toString(), UrlKey.toString()))
                    callMain()
                }
            }
            colocarImg.setOnClickListener {
                getRec()
            }
            config()
            editGame()
}


fun config() {
    alertDialog = SpotsDialog.Builder().setContext(this).build()

    storageReference = viewModelGame.Service.storage

}

fun getRec() {
    var rootFolder = sharedPrefs.getString(KEY_EMAIL, EMPTY_STRING).toString()
    storageReference = FirebaseStorage.getInstance().getReference( rootFolder+ "/gamesPics/" + addNameJogo.text.toString())
    val intent = Intent()
    intent.type = "image/*"
    intent.action = Intent.ACTION_GET_CONTENT
    alertDialog.show()
    startActivityForResult(Intent.createChooser(intent, "Selecione a imagem"), COD_IMG)
}

fun getUniqueId() = service.database.collection(service.coluna).document().id


fun callMain(){
    val intent = Intent(this,MainActivity::class.java)
    startActivity(intent)
}

fun editGame(){
    val name = intent.getSerializableExtra("name") as? String
    val data = intent.getSerializableExtra("lancamento") as? String
    val description = intent.getSerializableExtra("descricao") as? String
    val URL = intent.getSerializableExtra("url") as? String
    val key = intent.getSerializableExtra("key") as? String
    val edit = intent.getSerializableExtra("edit") as? Boolean
    if(edit == true){
        val jogo = Jogo(key!!,name!!,data!!,description!!,URL!!)
        setGameInEd(jogo)
    }
}

fun setGameInEd(game: Jogo){
    addNameJogo.setText(game.nome)
    criadoEm.setText(game.data)
    colocarDesc.setText(game.descricao)
    Picasso.get().load(game.URL).into(colocarImg)

}



override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode == CODE_IMG) {
        alertDialog.show()
        val uploadFile = storageReference.putFile(data!!.data!!)
        val task = uploadFile.continueWithTask { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Imagem Carrregada com sucesso!", Toast.LENGTH_SHORT)
                    .show()
            }
            storageReference!!.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                URL = downloadUri!!.toString()
                    .substring(0, downloadUri.toString().indexOf("&token"))
                Log.i("URL da Imagem", URL)
                alertDialog.dismiss()
                Picasso.get().load(URL).into(colocarImg)
                ImageSet = true

            }
        }
    }
}}
