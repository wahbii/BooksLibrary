package com.app.bookslibrary.dataManager.repository

import androidx.lifecycle.MutableLiveData
import com.app.bookslibrary.dataManager.Apis
import javax.inject.Inject

class BookRepository @Inject constructor(var apis: Apis ) {


    suspend fun getBooks(book:String) :String?{
        var call =apis.search(book)
        return when(call.isSuccessful&&call.body()!=null){
            true->{
                if(call.body()!=null) call.body()!! else "nothing"
            }
            else->{
                null
            }
        }

    }


}