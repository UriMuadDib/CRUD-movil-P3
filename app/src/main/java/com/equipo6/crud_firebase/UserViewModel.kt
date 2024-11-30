package com.equipo6.crud_firebase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class UserViewModel: ViewModel() {

    private val db = Firebase.firestore

    private var _listaUsers  = MutableLiveData<List<User>>(emptyList())
    val listaUsers: LiveData<List<User>> = _listaUsers

    init {
        obtenerUsers()
    }

    fun obtenerUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val resultado = db.collection("users").get().await()

                val users = resultado.documents.mapNotNull { it.toObject(User::class.java) }
                _listaUsers.postValue(users)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun agregarUser(user: User) {
        user.id = UUID.randomUUID().toString()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                db.collection("users").document(user.id).set(user).await()
                _listaUsers.postValue(_listaUsers.value?.plus(user))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun actualizarUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                db.collection("users").document(user.id).update(user.toMap()).await()
                _listaUsers.postValue(_listaUsers.value?.map { if (it.id == user.id) user else it })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun borrarUser(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                db.collection("users").document(id).delete().await()
                _listaUsers.postValue(_listaUsers.value?.filter { it.id != id })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}