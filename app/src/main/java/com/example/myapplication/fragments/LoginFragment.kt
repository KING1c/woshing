package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.misc.RepositoryViewModal
import com.example.myapplication.misc.Service
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginFragment : Fragment() {
   private lateinit var binding: FragmentLoginBinding
   private val viewModel: RepositoryViewModal by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }
    private var databaseServicesReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("services")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){

//            databaseServicesReference.setValue(listOf(
//                Service(
//                    name = "Мойка окон",
//                    cast = 500,
//                    time = 30
//                ),
//                Service(
//                    name = "Мойка салона",
//                    cast = 2500,
//                    time = 120
//                ),
//                Service(
//                    name = "Мойка колес",
//                    cast = 500,
//                    time = 20
//                ),
//            ))

            buttonRegistration.setOnClickListener {
                it.findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
            }
            buttonConfirm.setOnClickListener {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(editTextName.text.toString(), editTextPassword.text.toString())
                    .addOnCompleteListener{ task ->
                        if (task.isSuccessful) {
                            viewModel.loadUser()
                            viewModel.loadServices()
                            viewModel.loadOrder()
                            it.findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                        } else {
                            // Обработка ошибки
                        }
                    }
            }
        }
    }

}