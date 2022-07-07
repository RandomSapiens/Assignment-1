package com.example.myapplication.data
import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesRepository @Inject constructor(@ApplicationContext private val context: Context) {
    suspend fun writeToLocal(name: String, phoneNumber: String, email: String) =
        context.userPreferencesStore.updateData {
            it.toBuilder().setUsername(name).setPhoneNumber(phoneNumber).setEmail(email).build()
        }

    val readToLocal: Flow<Users> = context.userPreferencesStore.data.catch {
        if (this is Exception) {
            Log.d("main", "${this.message}")
        }
    }.map {
        val users = Users(it.username, it.phoneNumber, it.email)
        users
    }

}

