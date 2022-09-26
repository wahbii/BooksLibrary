package com.app.bookslibrary.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.bookslibrary.dataManager.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(var repository: BookRepository): ViewModel() {

     var  _bookLiveData :MutableLiveData<String?> = MutableLiveData()
     val bookLiveData:LiveData<String?> = _bookLiveData

     fun getBooks(bookname:String) =CoroutineScope(Dispatchers.IO).launch {
         var data = repository.getBooks(bookname)
         with(Dispatchers.Main){
             _bookLiveData.postValue(data)
         }
     }



}