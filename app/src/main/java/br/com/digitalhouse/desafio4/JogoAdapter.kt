package br.com.digitalhouse.desafio4

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.frag_jogo.view.*

class JogoAdapter(private val listGames: ArrayList<Jogo>, val listener: OnGameClickListener) : RecyclerView.Adapter<JogoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.frag_jogo, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tag = position
        holder.nomeCard.text = listGames[position].nome
        holder.dataCard.text = listGames[position].data
        Picasso.get().load(listGames[position].URL).into(holder.imgCard)
        holder.imgCard.setOnClickListener {
            val intent = Intent(holder.itemView.context, JogoDescricaoActivity::class.java)
            intent.putExtra("name", listGames[position].nome)
            intent.putExtra("lancamento", listGames[position].data)
            intent.putExtra("descricao", listGames[position].descricao)
            intent.putExtra("url", listGames[position].URL)
            intent.putExtra("key", listGames[position].id)
            holder.itemView.context.startActivity(intent)
        }
        holder.imgCard.setOnLongClickListener(holder)

    }





    interface OnGameClickListener {
        fun gameClick(position: Int)

    }

    override fun getItemCount() = listGames.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener {
        val nomeCard = view.nomeCard
        val dataCard = view.dataCard
        val imgCard = view.imgCard

        init {
            view.setOnLongClickListener(this)
        }

    override fun onLongClick(v: View?): Boolean {
        val position = adapterPosition
        if (RecyclerView.NO_POSITION != position) {
            listener.gameClick(position)
            return true
        }
        return false
        }
    }


}

