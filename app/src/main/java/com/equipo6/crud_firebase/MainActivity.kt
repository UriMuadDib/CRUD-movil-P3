package com.equipo6.crud_firebase

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.equipo6.crud_firebase.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: UserViewModel

    var userEdit = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        viewModel.listaUsers.observe(this) { users ->
            setupRecyclerView(users)
        }

        binding.btnUserAdd.setOnClickListener {
            val user = User(
                name = binding.userName.text.toString(),
                email = binding.userEmail.text.toString(),
                password = binding.userPassword.text.toString()
            )

            viewModel.agregarUser(user)

            binding.userName.setText("")
            binding.userEmail.setText("")
            binding.userPassword.setText("")
        }

        binding.btnUserUpdate.setOnClickListener {
            userEdit.name = ""
            userEdit.email = ""
            userEdit.password = ""

            userEdit.name = binding.userName.text.toString()
            userEdit.email = binding.userEmail.text.toString()
            userEdit.password = binding.userPassword.text.toString()

            viewModel.actualizarUser(userEdit)

            adapter.notifyDataSetChanged()

            binding.userName.setText("")
            binding.userEmail.setText("")
            binding.userPassword.setText("")
        }
    }

    fun setupRecyclerView(listaUsers: List<User>) {
        adapter = UserAdapter(listaUsers, ::borrarUser, ::actualizarUser)
        binding.rvUsers.adapter = adapter
    }

    fun borrarUser(id: String) {
        viewModel.borrarUser(id)
    }

    fun actualizarUser(user: User) {
        userEdit = user

        binding.userName.setText(userEdit.name)
        binding.userEmail.setText(userEdit.email)
        binding.userPassword.setText(userEdit.password)
    }
}