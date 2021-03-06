package com.aqa.proyectoclase.controlador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.aqa.proyectoclase.R
import com.aqa.proyectoclase.modelo.Message
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class ChatAddapter(val messages: ArrayList<Message>, val uid: String): RecyclerView.Adapter<ChatAddapter.ViewHolder>() {
    val EMISOR = 1
    val RECEPTOR = 2

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messages[position]
        holder.bind(message)
    }


    override fun getItemViewType(position: Int): Int {
        if(messages[position]?.emisor.equals(uid)){
            return EMISOR
        }else{
            return RECEPTOR
        }
    }

    override fun getItemCount(): Int {return messages.size}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        lateinit var view: View

        view = if(viewType == EMISOR) LayoutInflater.from(parent.context).inflate(R.layout.card_mensaje,parent,false)
        else LayoutInflater.from(parent.context).inflate(R.layout.card_mensaje_otro,parent,false)
        return ViewHolder(view)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtContent: TextView = itemView.findViewById(R.id.txtContent)
        val txtTime: TextView = itemView.findViewById(R.id.txtTime)
        val txtDate: TextView = itemView.findViewById(R.id.txtDate)
        fun bind(message: Message){//aqui
            val formatDate: DateFormat = SimpleDateFormat("dd MMMM yyyy")
            val formatTime: DateFormat = SimpleDateFormat("HH:mm")
            var time: Date = Date()
            var timeNow: Date = Date()

            if (message != null) {
                time.time = message.timestamp
                txtContent.text = message.content
            }
            timeNow.time = System.currentTimeMillis()
            if(formatDate.format(time).equals(formatDate.format(timeNow))){
                txtDate.text = "Hoy"
            }else if(TimeUnit.DAYS.convert(timeNow.time - time.time,TimeUnit.MILLISECONDS) == 1L ){
                txtDate.text = "Ayer"
            }else{
                txtDate.text = formatDate.format(time)
            }
            txtTime.text = formatTime.format(time)
        }
    }
}