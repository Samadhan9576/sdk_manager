package com.samadhan.sdkmanager.presentation.controller

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.samadhan.sdkmanager.viewModel.LoginViewModel


@Composable
fun LoginController(navController: NavController,loginViewModel: LoginViewModel = hiltViewModel()) {
    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    var rememberMe = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FB)), // light background
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.85f)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Login",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                // Email
                Text(text = "Email", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                OutlinedTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    placeholder = { Text("Enter email") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFEFF5FF),
                    )
                )

                // Password
                Text(text = "Password", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    placeholder = { Text("Enter password") },
                    visualTransformation = PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFEFF5FF),
                    )
                )

                // Remember Me + Forgot Password
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = rememberMe.value,
                            onCheckedChange = { rememberMe.value = it }
                        )
                        Text(text = "Remember me")
                    }
                    Text(
                        text = "Forgot Password?",
                        color = Color.Blue,
                        fontSize = 14.sp,
                        modifier = Modifier.clickable { /* Handle forgot password */ }
                    )
                }

                // Sign In Button
                Button(
                    onClick = {
                        loginViewModel.login(email.value,password.value,rememberMe.value)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4C4CFF) // Blue button
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(top = 16.dp)
                ) {
                    Text(text = "Sign In", color = Color.White, fontSize = 16.sp)
                }
            }
        }
    }
}
