package com.samadhan.sdkmanager.presentation.controller

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.samadhan.sdkmanager.domain.UserCredentialsDataStore
import com.samadhan.sdkmanager.domain.event.LoginEvent
import com.samadhan.sdkmanager.domain.viewModel.LoginViewModel
import com.samadhan.sdkmanager.presentation.navigation.Screens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginController(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val email = remember { mutableStateOf("samadhanm@siddhatech.com") }
    val password = remember { mutableStateOf("Qwertyuiop@1234") }
    val rememberMe = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val state = loginViewModel.uiState.collectAsState()
    val dataStore = UserCredentialsDataStore(context)
    val userMap = dataStore.getUsers.collectAsState(initial = emptyMap())
    val expanded = remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        loginViewModel.events.collect { event ->
            Log.e("TAG", "LoginController: $event", )
            when (event) {
                is LoginEvent.OnLoginSuccess -> {
                    if (rememberMe.value) {
                        dataStore.saveUser(email.value, password.value)
                    }
                    navController.navigate(Screens.DashboardController.route)
                }

                is LoginEvent.ShowSnackBar -> {

                }
                else ->{}
            }
        }
    }
    Surface {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FB)),
            contentAlignment = Alignment.Center
        )
        {
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

                    Text(text = "Email", fontSize = 14.sp, fontWeight = FontWeight.Medium)

                    ExposedDropdownMenuBox(
                        expanded = expanded.value,
                        onExpandedChange = { expanded.value = !expanded.value}
                    ) {
                        OutlinedTextField(
                            value = email.value,
                            onValueChange = { email.value = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                                .padding(vertical = 8.dp),
                            placeholder = { Text("Enter email") },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFFEFF5FF),
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                }
                            )
                        )

                        ExposedDropdownMenu(
                            expanded = expanded.value,
                            onDismissRequest = { expanded.value = false }
                        ) {
                            userMap.value.forEach { user ->
                                DropdownMenuItem(
                                    text = { Text(user.key) },
                                    onClick = {
                                        email.value = user.key
                                        password.value = user.value
                                        expanded.value = false
                                    }
                                )
                            }
                        }
                    }

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
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                            }
                        )
                    )

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
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "Forgot Password?",
                            color = Color.Blue,
                            fontSize = 14.sp,
                            modifier = Modifier.clickable { }
                        )
                    }

                    Button(
                        onClick = {
                            loginViewModel.login(email.value, password.value, rememberMe.value)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4C4CFF)
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
        CircularProgress(state.value.isLoading)
    }
}
@Composable
fun CircularProgress(isShowing: Boolean) {
    if (isShowing) {
        Dialog(onDismissRequest = { }) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color.White, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
