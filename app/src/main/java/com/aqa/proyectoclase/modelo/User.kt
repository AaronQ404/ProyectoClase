package com.aqa.proyectoclase.modelo

data class User (var uid: String, var username:String, var email:String, var profilePic:String) {
    constructor() : this("","","","")
}