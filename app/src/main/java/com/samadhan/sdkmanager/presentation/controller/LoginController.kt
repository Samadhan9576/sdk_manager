package com.samadhan.sdkmanager.presentation.controller

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.CheckboxDefaults
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
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.samadhan.sdkmanager.R
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
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val rememberMe = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val state = loginViewModel.uiState.collectAsState()
    val dataStore = UserCredentialsDataStore(context)
    val userMap = dataStore.getUsers.collectAsState(initial = emptyMap())
    val expanded = remember { mutableStateOf(false) }
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = Color.Transparent,
        darkIcons = true
    )
    BackHandler {  }

    LaunchedEffect(true) {
        loginViewModel.events.collect { event ->
            Log.e("TAG", "LoginController: $event", )
            when (event) {
                is LoginEvent.OnLoginSuccess -> {
                    if (rememberMe.value) {
                        dataStore.saveUser(email.value, password.value)
                    }
                    context.showLocalNotification(
                        "Login Successful!", "Welcome to SiddhaPole ${loginViewModel.uiState.value.response?.userDetails?.userName?.dropLast(1)}"
                    )
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
                .background(Color(0xFFF8F9FB))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    focusManager.clearFocus()
                },
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.85f),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFD9E1E6))
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Login",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 20.dp),
                        color = Color(0xFF121A1F)
                    )
                    Text(text = "Email", fontSize = 14.sp, fontWeight = FontWeight.Medium,color = Color(0xFF131B20))
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
                            placeholder = { Text("Enter email", color = Color(0xFF26282E)) },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFFEDF3FD),
                                unfocusedContainerColor = Color(0xFFE0E1EB),
                                focusedTextColor = Color(0xFF26282E),
                                unfocusedTextColor = Color(0xFF26282E),
                                focusedIndicatorColor = Color(0xFF495D91),
                                unfocusedIndicatorColor = Color(0xFF495D91)
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                }
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expanded.value,
                            onDismissRequest = { expanded.value = false },
                            containerColor = Color(0xFFEAECF6)
                        ) {
                            userMap.value.forEach { user ->
                                DropdownMenuItem(
                                    text = { Text(user.key, color = Color(0xFF26282E)) },
                                    onClick = {
                                        email.value = user.key
                                        password.value = user.value
                                        expanded.value = false
                                        focusManager.clearFocus()
                                    },
                                )
                            }
                        }
                    }
                    Text(text = "Password", fontSize = 14.sp, fontWeight = FontWeight.Medium,color = Color(0xFF131B20))
                    OutlinedTextField(
                        value = password.value,
                        onValueChange = { password.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        placeholder = { Text("Enter password", color = Color(0xFF26282E)) },
                        visualTransformation = PasswordVisualTransformation(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFEDF3FD),
                            unfocusedContainerColor = Color(0xFFE0E1EB),
                            focusedTextColor = Color(0xFF26282E),
                            unfocusedTextColor = Color(0xFF26282E),
                            focusedIndicatorColor = Color(0xFF495D91),
                            unfocusedIndicatorColor = Color(0xFF495D91)
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
                                onCheckedChange = { rememberMe.value = it },
                                colors = CheckboxDefaults.colors(checkedColor = Color(0xFF26282E), uncheckedColor = Color(0xFF26282E),checkmarkColor = Color.White)
                            )
                            Text(text = "Remember me",color = Color(0xFF131B20))
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

fun Context.showLocalNotification(title: String, message: String) {
    val channelId = "default_channel"
    val notificationManager =
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(channelId, "General Notifications",
            NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)
    }

    val builder = NotificationCompat.Builder(applicationContext, channelId)
        .setContentTitle(title)
        .setContentText(message)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setColor(ContextCompat.getColor(this, R.color.purple_500))
        .setAutoCancel(true)

    notificationManager.notify(1001, builder.build())
}