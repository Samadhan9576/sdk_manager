package com.samadhan.sdkmanager

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.samadhan.sdk.SdkInitializer
import com.samadhan.sdkmanager.ui.theme.SDKManagerTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        SdkInitializer.init("https://jsonplaceholder.typicode.com/")

        setContent {
            SDKManagerTheme {
                GlobalScope.launch {
                    try {
                        val user = SdkInitializer.getUserUseCase("1")
                        Log.d("User", user.toString())
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.e("SDK", "Error: ${e.message}")
                    }

                }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )


                }
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