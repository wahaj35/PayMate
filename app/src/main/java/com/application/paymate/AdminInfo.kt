package com.application.paymate

import android.os.Bundle
import android.text.InputFilter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.application.paymate.databinding.FragmentAdminInfoBinding

class AdminInfo : Fragment() {
    private lateinit var binding: FragmentAdminInfoBinding
    private lateinit var CNICValidator: CNICValidator
    private lateinit var text:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_info, container, false)

        //Setting up minimum length for CNIC number and pin
        val minPinLength = 4 // Minimum Pin length
        val minCnicLength = 13 //Minimum CNIC number length
        val pinLengthFilter = arrayOf<InputFilter>(InputFilter.LengthFilter(minPinLength))
        val cnincLengthFilter = arrayOf<InputFilter>(InputFilter.LengthFilter(minCnicLength))
        binding.createPasswordEditText.filters = pinLengthFilter
        binding.confirmPasswordEditText.filters = pinLengthFilter
        binding.enterCNICEditText.filters = cnincLengthFilter

        //Setting up TextWatcher for CNIC number to check the length of number.
        CNICValidator = CNICValidator(binding.enterCNICEditText, "Invalid")
        binding.enterCNICEditText.addTextChangedListener(CNICValidator)

        //Setting up TextWatcher for username to check if the username is starting with admin or not and if there is any space in between.
        val usernameValidator = UsernameValidator(object : UsernameValidatorCallBack{
            override fun onInputValidated(isValid: Boolean) {
               if(isValid){
                  // Store the input data from the user to the firebase.
               } else {
                   binding.enterUserNameEditText.error = "Invalid"
               }
            }
        },binding.enterUserNameEditText)
        binding.enterUserNameEditText.addTextChangedListener(usernameValidator)
        return binding.root
    }
}