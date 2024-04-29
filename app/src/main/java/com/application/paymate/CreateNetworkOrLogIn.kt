package com.application.paymate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.application.paymate.databinding.FragmentCreateNetworkOrLogInBinding

class CreateNetworkOrLogIn : Fragment() {
    lateinit var binding:FragmentCreateNetworkOrLogInBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_create_network_or_log_in, container, false)
        binding.firstOptionCardView.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_createNetworkOrLogIn_to_adminInfo)
        }


        return binding.root
    }
}