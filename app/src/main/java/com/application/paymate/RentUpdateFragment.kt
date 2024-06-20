package com.application.paymate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.application.paymate.databinding.FragmentRentUpdateBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RentUpdateFragment : Fragment() {
    private lateinit var binding: FragmentRentUpdateBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rent_update, container, false)

        val existingRentAmount = sharedViewModel.rentAmount.value.toString()
        binding.updateRentEditText.setText(existingRentAmount)

        val mateName = sharedViewModel.mateName.value.toString()

        val mateIdNode = sharedViewModel.mateNode.value.toString()

        //Setting up items of the Drop down spinner view
        val dropDownItems = arrayOf("Add", "Subtract")
        val dropDownView = binding.selectOptionDropDown
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, dropDownItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dropDownView.adapter = adapter

        //Setting up logic for onItemSelected of the drop down.
        dropDownView.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        binding.addIcon.setImageResource(R.drawable.baseline_add_24)

                        //Making an instance of the UpdateAmount class and calling the updateAmount method to add the amount in realtime database
                        val updateAmountObject = UpdateAmount(mateName)
                        view?.let {
                            updateAmountObject.updateAmount(
                                binding.updateChangesButton,
                                mateIdNode,
                                "update_rent",
                                "plus",
                                binding.enterRentAmountEditText,
                                requireActivity(), it
                            )
                        }
                    }

                    1 -> {
                        binding.addIcon.setImageResource(R.drawable.baseline_minimize_24)
                        //Making an instance of the UpdateAmount class and calling the updateAmount method to subtract the amount in realtime database
                        val updateAmountObject = UpdateAmount(mateName)
                        view?.let {
                            updateAmountObject.updateAmount(
                                binding.updateChangesButton,
                                mateIdNode,
                                "update_rent",
                                "minus",
                                binding.enterRentAmountEditText,
                                requireActivity(), it
                            )
                        }
                    }
                }
            }


            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        return binding.root
    }


    //Method to add the rent amount
    private fun addRent() {
        val mateIdNode = sharedViewModel.mateNode.value.toString()
        binding.updateChangesButton.setOnClickListener {
            val inputFieldChecker = InputFieldEmptyOrNot()
            if (inputFieldChecker.inputFieldEmptyOrNot(binding.enterRentAmountEditText)) {
                Toast.makeText(context, "Please Enter Amount First", Toast.LENGTH_SHORT).show()
            } else {
                if (NetworkUtil.isNetworkAvailable(requireContext())) {
                    val enteredRentAmount = binding.enterRentAmountEditText.text.toString()
                    val database = FirebaseDatabase.getInstance()
                    val databaseReference = database.getReference("admin_profiles")
                        .child(FirebaseAuth.getInstance().currentUser!!.uid).child("Mates")
                        .child(mateIdNode).child("rent_amount")
                    databaseReference.get().addOnCompleteListener { snapshot ->
                        val currentRentAmount = snapshot.result.value.toString()
                        val newRentAmount = currentRentAmount.toInt() + enteredRentAmount.toInt()
                        databaseReference.setValue(newRentAmount.toString())
                        Toast.makeText(context, "Rent Updated", Toast.LENGTH_SHORT).show()
                        view?.findNavController()
                            ?.navigate(R.id.action_rentUpdateFragment2_to_allMates2)
                    }
                } else Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun subtractRent() {
        val mateIdNode = sharedViewModel.mateNode.value.toString()
        binding.updateChangesButton.setOnClickListener {
            val inputFieldChecker = InputFieldEmptyOrNot()
            if (inputFieldChecker.inputFieldEmptyOrNot(binding.enterRentAmountEditText)) {
                Toast.makeText(context, "Please Enter Amount First", Toast.LENGTH_SHORT).show()
            } else {
                if (NetworkUtil.isNetworkAvailable(requireContext())) {
                    val enteredRentAmount = binding.enterRentAmountEditText.text.toString()
                    val database = FirebaseDatabase.getInstance()
                    val databaseReference = database.getReference("admin_profiles")
                        .child(FirebaseAuth.getInstance().currentUser!!.uid).child("Mates")
                        .child(mateIdNode).child("rent_amount")
                    databaseReference.get().addOnCompleteListener { snapshot ->
                        val currentRentAmount = snapshot.result.value.toString()
                        val newRentAmount = currentRentAmount.toInt() - enteredRentAmount.toInt()

                        //Checking if the new rent amount is negative or not if yes then do not update the rent amount
                        if (newRentAmount < 0) {
                            Toast.makeText(
                                context,
                                "Rent Amount cannot be negative",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            databaseReference.setValue(newRentAmount.toString())
                            Toast.makeText(context, "Changes Saved", Toast.LENGTH_SHORT).show()
                            view?.findNavController()
                                ?.navigate(R.id.action_rentUpdateFragment2_to_allMates2)
                        }
                    }
                } else Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show()
            }
        }
    }

}