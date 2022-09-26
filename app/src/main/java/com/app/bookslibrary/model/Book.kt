package com.app.bookslibrary.model

data class Book(
    var id:String,
    var name :String,
    var dowloadUrl:String,
    var urlImage:String,
    var publisher:String,
    var year:String,
    var pages:String,
    var authors:String
) {

    override fun toString(): String {
        return "id = $id  name = $name  url = $dowloadUrl   publisher = $publisher image = $urlImage + pages = $pages  year = $year"
    }
}