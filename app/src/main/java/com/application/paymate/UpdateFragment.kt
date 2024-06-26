package com.application.paymate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.application.paymate.databinding.FragmentUpdateBinding

class UpdateFragment : Fragment() {
    private lateinit var binding: FragmentUpdateBinding
    private val sharedViewModel:SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_update, container, false)

        //Retrieving data from the sharedViewModel
        val rentAmount = sharedViewModel.rentAmount.value.toString()
        val otherAmount = sharedViewModel.otherAmount.value.toString()
        val walletAmount = sharedViewModel.walletAmount.value.toString()

        //Overriding the text of the Text View to update the UI
       binding.rentUpdateEditText.setText(rentAmount)
        binding.otherUpdateEditText.setText(otherAmount)
        binding.walletUpdateEditText.setText(walletAmount)


        //Setting up click listeners for the edit texts
        binding.rentUpdateEditText.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_updateFragment2_to_rentUpdateFragment2)
        }

        binding.otherUpdateEditText.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_updateFragment2_to_otherDueUpdateFragment2)
        }

        binding.walletUpdateEditText.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_updateFragment2_to_walletUpdateFragment4)
        }

        return binding.root
    }
}