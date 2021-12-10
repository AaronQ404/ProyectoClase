package com.aqa.proyectoclase.modelo

data class Post(val userId: String, val title : String, val content:String, val image : String? = null) {
    constructor() : this("","","")
}