package com.samadhan.sdkmanager

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.samadhan.sdk.SdkInitializer
import com.samadhan.sdkmanager.domain.firebase.MyFirebaseMessagingService
import com.samadhan.sdkmanager.presentation.navigation.SetUpNavGraph
import com.samadhan.sdkmanager.ui.theme.SDKManagerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        MyFirebaseMessagingService()
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                Log.d("FCM_TOKEN", token)
            }
        }
        enableEdgeToEdge()
        SdkInitializer.init("https://jsonplaceholder.typicode.com/","https://li1761-109.members.linode.com:8096/")
        setContent {
            val viewModel: UserViewModel = hiltViewModel()
            val userState = viewModel.uiState.value
            Log.e("TAG", "onCreate: ${userState.user}", )
            SDKManagerTheme {
                val navController = rememberNavController()
                SetUpNavGraph(navController)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SDKManagerTheme {
        Greeting("Android")
    }
}