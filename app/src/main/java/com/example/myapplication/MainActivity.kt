package com.example.myapplication

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.Purple500
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel:MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Homepage(mainViewModel)
//                    Homepage()
                }

            }
        }
    }
}

private fun isEmailValid(email: String): Boolean {
    return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Composable
fun Homepage(mainViewModel: MainViewModel) {
//    val user by mainViewModel.readToLocal.collectAsState(initial = Users("","",""))
//    Log.d("Homepage","email is ${user.email} name is ${user.username}  phoneNumber is ${user.phoneNumber}")

//    var text by remember { mutableStateOf(user.username) }
//    var mobileNumber by remember { mutableStateOf(user.phoneNumber) }
    val (email,setEmail) = remember { mutableStateOf("") }
    val (username,setUsername) = remember { mutableStateOf("") }
    val (phoneNumber,setPhoneNumber) = remember { mutableStateOf("") }


    LaunchedEffect(key1 = Unit){
        mainViewModel.readToLocal.collect {
            setEmail.invoke(it.email)
            setPhoneNumber.invoke(it.phoneNumber)
            setUsername.invoke(it.username)
        }
    }


    var enabled by remember { mutableStateOf(false) }



    Column {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.2f)
                .fillMaxWidth()
                .background(color = Purple500)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color.White)
                .border(width = 1.dp, color = Color.Black)
                .padding(10.dp),
//        verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_baseline_account_circle_24),
                contentDescription = "",
                modifier = Modifier
                    .size(100.dp)
                    .padding(10.dp)
            )
            TextField(
                value = username,
                onValueChange = { newText ->
                    setUsername.invoke(newText)
                },
                label = { Text(text = "Name") },
                placeholder = { Text(text = "Username") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )

            TextField(
                value = phoneNumber,
                onValueChange = {
                    setPhoneNumber.invoke(it)
                    enabled = it.length == 10 && isEmailValid(email)
//                    println("State : $enabled")
//                    println("State : ${mobileNumber.length == 10}")
//                    println("State : ${isEmailValid(emailtext.toString())}")
//                    println("State : ${emailtext.toString()}")
//                    println("State: sawan ${isEmailValid("sawan.brb@gmail.com")}")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(text = "Phone") },
                placeholder = { Text(text = "1234567890") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )

            TextField(
                value = email,
                onValueChange = { newText ->
                    setEmail.invoke(newText)
                    enabled = phoneNumber.length == 10 && isEmailValid(newText)
                },
                label = { Text(text = "Email") },
                placeholder = { Text(text = "abc@gmail.com") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )

            Button(
                onClick = {
                    mainViewModel.writeToLocal(username,phoneNumber,email)
                    enabled = false
                    //your onclick code here
                }, elevation = ButtonDefaults.elevation(
                    defaultElevation = 10.dp,
                    pressedElevation = 15.dp,
                    disabledElevation = 0.dp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .absolutePadding(bottom = 5.dp),
                enabled = enabled
            ) {
                Text(text = "Save Details")
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    MyApplicationTheme {
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colors.background
//        ) {
//            Homepage(mainViewModel = null)
//        }
//    }
//}

