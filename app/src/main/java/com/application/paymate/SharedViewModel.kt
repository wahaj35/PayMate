package com.application.paymate

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel:ViewModel(){

    //To store admin name
    private var _adminName = MutableLiveData("")
    var adminName:MutableLiveData<String> = _adminName

    //To store admin email
    private var _adminEmail = MutableLiveData("")
    var adminEmail:MutableLiveData<String> = _adminEmail
}