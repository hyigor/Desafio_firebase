package br.com.digitalhouse.desafio4

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(val Service: Repositorio):ViewModel() {

    val resListaJogos = MutableLiveData<ArrayList<Jogo>>()
    val enviar = MutableLiveData<Boolean>()
    val colecao = Service.collection

    fun setValueLiveDataGame(listGame: ArrayList<Jogo>){
        resListaJogos.value = listGame
    }


    fun AtuaJogos(jogo: Jogo){
        colecao.document(jogo.id).set(jogo).addOnSuccessListener {
            enviar.value = true
        }.addOnCanceledListener {
            enviar.value = false

        }
    }

    fun getJogo(){
        colecao.get().addOnSuccessListener { documents ->
            var listGames = arrayListOf<Jogo>()
            for (document in documents){
                val jogo: Jogo = document.toObject(Jogo::class.java)
                listGames.add(jogo)
                Log.i("Guardado!", document.toString())
            }
            resListaJogos.value = listGames
        }
    }

    fun deleJogos(idJogo: String){
        colecao.document(idJogo).delete().addOnSuccessListener {

        }.addOnCanceledListener {

        }
    }
    fun enviarJogo(jogo: Jogo){
        colecao.document(jogo.id).set(jogo).addOnSuccessListener {
            enviar.value = true
        }.addOnCanceledListener {
            enviar.value = false
        }
    }




}