package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.UserPreferencesRepository
import com.example.myapplication.data.Users
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: UserPreferencesRepository) :ViewModel(){
    fun writeToLocal(userName:String,phoneNumber:String,email:String)=viewModelScope.launch {
        mainRepository.writeToLocal(userName,phoneNumber,email)
    }
    val readToLocal=mainRepository.readToLocal
//    val _userData= MutableStateFlow(Users("","",""))
//    fun updateName(name:String){
//        val updatedUserData =_userData.value.copy(username = name)
//        _userData.tryEmit(updatedUserData)
//    }
//
//    init{
//        mainRepository.readToLocal.collect {
//            _userData.emit(it)
//        }
//
//    }
//
//    fun saveData(){
//        val currentdata=_userData.value
//        writeToLocal(currentdata.username,currentdata.phoneNumber,currentdata.email)
//    }
}