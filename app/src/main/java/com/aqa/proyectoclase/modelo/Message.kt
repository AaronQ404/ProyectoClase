package com.aqa.proyectoclase.modelo

data class Message( val emisor:String, val content: String,val timestamp:Long){
    constructor() : this("","",0)
}
