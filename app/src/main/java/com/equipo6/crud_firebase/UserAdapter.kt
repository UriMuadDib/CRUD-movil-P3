package com.equipo6.crud_firebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(
    var listaUsers: List<User>,
    val onBorrarClick: (String) -> Unit,
    val onActualizarClick: (User) -> Unit
): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val cvUser: CardView = itemView.findViewById(R.id.cvUser)
        val userName: TextView = itemView.findViewById(R.id.userName)
        val userEmail: TextView = itemView.findViewById(R.id.userEmail)
        val userPassword: TextView = itemView.findViewById(R.id.userPassword)
        val btnBorrar: ImageButton = itemView.findViewById(R.id.btnBorrar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        val user = listaUsers[position]

        holder.userName.text = user.name
        holder.userEmail.text = user.email
        holder.userPassword.text = user.password

        holder.btnBorrar.setOnClickListener {
            onBorrarClick(user.id)
        }

        holder.cvUser.setOnClickListener {
            onActualizarClick(user)
        }
    }

    override fun getItemCount(): Int {
        return listaUsers.size
    }
}