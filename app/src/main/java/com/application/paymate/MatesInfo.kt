package com.application.paymate

class MatesInfo {
    var name: String? = null
    var phone: String? = null
    var mate_id:String? = null

    // Add a no-argument constructor
    constructor() {}

    constructor(name :String,phoneNumber:String,id:String) {
        this.name = name
        this.phone = phoneNumber
        this.mate_id = id
    }
}