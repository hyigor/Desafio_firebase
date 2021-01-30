package br.com.digitalhouse.desafio4

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), JogoAdapter.OnGameClickListener {

    private lateinit var jogoAdapater: JogoAdapter
    private var service = Repositorio()
    var listaJogos = ArrayList<Jogo>()


    private val viewModel by viewModels<MainViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(service) as T
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRegistrarJogo.setOnClickListener{
            val intent = Intent(this, RegistrarJogoActivity::class.java)
            startActivity(intent)
        }

        initRecycler()

        viewModel.getJogo()

        viewModel.resListaJogos.observe(this){
            initAdapter(it)
            listaJogos = it
        }

    }

    private fun initRecycler(){
        rv_cards.layoutManager = GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
        rv_cards.setHasFixedSize(true)
    }

    private fun initAdapter(listGames: ArrayList<Jogo>){
        jogoAdapater = JogoAdapter(listGames, this)
        rv_cards.adapter = jogoAdapater
    }

    override fun gameClick(position: Int) {
        val gameSelect = listaJogos.get(position)
        creatAlert(gameSelect)
        Log.i("teste de click", "Clickou")
    }

    fun creatAlert(jogo: Jogo){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setMessage("Deseja deletar esse game?")
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton("Sim",{ dialogInterface: DialogInterface, i: Int ->
            viewModel.deleJogos(jogo.id)
            updateList()
        })

        alertDialog.setNegativeButton("Não", { dialogInterface: DialogInterface, i: Int ->

        })

        alertDialog.create().show()
    }

    fun updateList(){
        viewModel.getJogo()
        viewModel.resListaJogos.observe(this){
            initAdapter(it)
        }
    }
}