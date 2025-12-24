package com.sushaant.ca2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sushaant.ca2.ui.theme.CA2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CA2Theme {
                navigation()
            }
        }
    }
}

@Composable
fun login(nav: NavHostController) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var selectedOpt by remember { mutableStateOf(false) }
    Column(modifier = Modifier.statusBarsPadding().fillMaxSize().background(Color.LightGray)
        .padding(25.dp)
        , horizontalAlignment = Alignment.CenterHorizontally
        , verticalArrangement = Arrangement.Center) {

        Text("LOGIN",
            modifier = Modifier.padding(20.dp)
                .offset(0.dp,(-75).dp)
                .background(Color.Transparent),
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(20.dp))

        TextField(value = email,
            onValueChange = {email = it},
            label = {Text("Email")},
            modifier = Modifier.fillMaxWidth().padding(bottom = 15.dp))

        TextField(value = pass,
            onValueChange = {pass = it},
            label = {Text("Password")},
            modifier = Modifier.fillMaxWidth().padding(bottom = 15.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        Row(modifier = Modifier.offset(0.dp,(-7).dp),
            verticalAlignment = Alignment.CenterVertically) {
            Checkbox(selectedOpt,
                onCheckedChange = { selectedOpt = it})
            Text("Remember Me")
        }

        val context = LocalContext.current
        Button(onClick = {
            if(email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(context,"Fields are empty",
                    Toast.LENGTH_SHORT).show()
            }
            else if(pass == "SV345" && email == "sv06@gmail.com") {
                Toast.makeText(context,"Log In Successful",
                    Toast.LENGTH_SHORT).show()
                nav.navigate("welcome/$selectedOpt")
            }
            else {
                Toast.makeText(context,"Invalid credentials",
                    Toast.LENGTH_LONG).show()
            }

        }, modifier = Modifier.width(100.dp).height(40.dp)
            .offset((-5).dp,0.dp)) {
            Text("Login")
        }
    }
}

@Composable
fun welcome(nav: NavHostController, value: Boolean) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if(value) {
            Text("Welcome\nRemember Me Selected")
        }
        else {
            Text("Welcome")
        }
        Spacer(modifier = Modifier.height(15.dp))
        Button(onClick = {
            nav.popBackStack()
        }) {
            Text("Logout")
        }
    }
}

@Composable
fun navigation() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "login") {
        composable("login") {
            login(nav)
        }
        composable("welcome/{value}") { backStack ->
            val value = backStack.arguments?.getString("value").toBoolean()
            welcome(nav, value)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun view(){
    CA2Theme {
        navigation()
    }
}