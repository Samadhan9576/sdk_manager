package com.samadhan.sdkmanager.presentation.controller

import android.Manifest
import android.graphics.Bitmap
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.samadhan.sdkmanager.domain.event.LoginEvent
import com.samadhan.sdkmanager.domain.viewModel.DashboardViewModel
import com.samadhan.sdkmanager.domain.viewModel.DetailsState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DashboardController(
    navController: NavHostController,
    dashboardViewModel: DashboardViewModel = hiltViewModel()
) {
    val state = dashboardViewModel.uiState
    Log.e("TAG", "DashboardController: ${dashboardViewModel.uiDetailsState.value.response}")
    val detailState = dashboardViewModel.uiDetailsState.value
    val detailsPopUp = remember { mutableStateOf(false) }
    val polePopUp = remember { mutableStateOf(false) }
    val isLoading = remember { mutableStateOf(false) }
    LaunchedEffect(true) {
        dashboardViewModel.events.collect { event ->
            Log.e("TAG", "DashboardController: $event", )
            if (event is LoginEvent.PoleDetailSuccess) {
                if(event.clickId == 1){
                    detailsPopUp.value = true
                }else{
                    dashboardViewModel.getUserSelectedOptions(event.id)
                    polePopUp.value = true
                }
            }
            if (event is LoginEvent.isLoading) {
                isLoading.value = true
            }
        }
    }
    val cameraPermission = Manifest.permission.CAMERA

    var isScannerVisible = remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            isScannerVisible.value = true
        }
    }

    Surface {
        Scaffold { innerPadding ->
            if (isScannerVisible.value) {
                QRScannerScreen(onClose = {
                    isScannerVisible.value = false
                }, onResult = {
                    isScannerVisible.value = false
                    dashboardViewModel.submitQR(it)
                    Log.e("TAG", "QRScannerScreen: $it" )
                })
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp)
                )
                {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Active Polls",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Button(onClick = {
                            permissionLauncher.launch(cameraPermission)
                        }) {
                            Text("Scan Poll")
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = "",
                        onValueChange = { },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Search") },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF3F4F6))
                            .padding(vertical = 8.dp, horizontal = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Poll", fontWeight = FontWeight.SemiBold)
                        Text("Actions", fontWeight = FontWeight.SemiBold)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Column(
                        modifier = Modifier
                    ) {
                        dashboardViewModel.uiState.value.response?.content?.forEach { poll ->
                            PollRow(
                                pollName = poll.title,
                                date = poll.pollDate,
                                id = poll.id
                            ) { clickId ->
                                dashboardViewModel.getPoleDetail(poll.id, clickId)
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(onClick = {}, enabled = false) {
                            Text("< Previous")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("1", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = {}, enabled = false) {
                            Text("Next >")
                        }
                    }
                }
                CircularProgress(isLoading.value)
                PoleDetailDialog(detailsPopUp, detailState)
                SavePoleDetailDialog(
                    polePopUp, detailState, onSubmit = { selected ->
                        polePopUp.value = false
                        dashboardViewModel.savePole(selected.toInt())
                        println("Submitted: $selected")
                    },
                    onClose = {
                        polePopUp.value = false
                        println("Closed")
                    },
                    onRemoveVote = { selected ->
                        polePopUp.value = false
                        dashboardViewModel.removePole(selected)
                        println("Vote Removed")
                    }
                )
            }
        }
    }
}


@Composable
fun PollRow(pollName: String, date: String, id: Int, onclick: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = pollName, fontWeight = FontWeight.Medium)
            Text(
                text = formatDate(date),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            IconButton(
                onClick = {
                    onclick(1)
                },
                modifier = Modifier.background(Color(0xFFFFC107), CircleShape)
            ) {
                Icon(Icons.Default.Info, contentDescription = "Info", tint = Color.White)
            }
            IconButton(
                onClick = { /* QR */ },
                modifier = Modifier.background(Color(0xFF2196F3), CircleShape)
            ) {
                Icon(Icons.Default.Settings, contentDescription = "QR", tint = Color.White)
            }
            IconButton(
                onClick = { onclick.invoke(3) },
                modifier = Modifier.background(Color(0xFF4CAF50), CircleShape)
            ) {
                Icon(Icons.Default.Check, contentDescription = "Done", tint = Color.White)
            }
        }
    }
}


fun formatDate(inputDate: String): String {
    val parsedDate = LocalDate.parse(inputDate, DateTimeFormatter.ISO_DATE)
    val formatter = DateTimeFormatter.ofPattern("d MMM yyyy")
    return parsedDate.format(formatter)
}


@Composable
fun PoleDetailDialog(showDialog: MutableState<Boolean>, detailState: DetailsState) {
    val values = detailState.response?.poll
    if (showDialog.value) {
        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(usePlatformDefaultWidth = true)
        ) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)) {
                    InfoRow(label = "Description:", value = values?.description?:"")
                    InfoRow(label = "Meal Type:", value = values?.title?:"")
                    InfoRow(label = "Poll Start Date:", value = values?.startDate?:"")
                    InfoRow(label = "Poll End Date:", value = values?.endDate?:"")
                    InfoRow(label = "Poll Date:", value = values?.pollDate?:"")
                    InfoRow(label = "Poll Option Type:", value = "single")
                    InfoRow(label = "Options:", value = values?.options?.first()?.optionText?:"")
                    Button(
                        onClick = { showDialog.value = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("OK")
                    }
                }
            }
        }
    }
}

@Composable
fun SavePoleDetailDialog(showDialog: MutableState<Boolean>, detailState: DetailsState,
                         onSubmit: (String) -> Unit,
                         onClose: () -> Unit,
                         onRemoveVote: (Int) -> Unit) {
    val values = detailState.response?.poll
    if (showDialog.value) {
        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(usePlatformDefaultWidth = true)
        ) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, shape = RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {

                    Text(
                        text = values?.title?:"",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = values?.pollDate?:"",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                    )

                    Text(text = values?.description?:"")

                    Text(
                        text = "Choose an option:",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
                    )

                    var selectedOption = remember { mutableStateOf<String?>(null) }

                    values?.options?.forEach { option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedOption.value = option.id.toString() }
                                .padding(4.dp)
                        ) {
                            RadioButton(
                                selected = selectedOption.value == option.id.toString(),
                                onClick = { selectedOption.value = option.id.toString() }
                            )
                            Text(text = option.optionText?:"", modifier = Modifier.padding(start = 8.dp))
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { selectedOption.let { it.value?.let { it1 -> onSubmit(it1) } } },
                            enabled = selectedOption.value != null,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Gray,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Submit")
                        }

                        Button(
                            onClick = onClose,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Close")
                        }
                    }

                    OutlinedButton(
                        onClick = { values?.id?.let { onRemoveVote(it) } },
                        enabled = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray)
                    ) {
                        Text("Remove Vote")
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

fun generateQRBitmap(text: String): Bitmap {
    val size = 600
    val black = androidx.compose.ui.graphics.Color.Black.toArgb()
    val white = androidx.compose.ui.graphics.Color.White.toArgb()
    val bits = QRCodeWriter().encode(
        text,
        BarcodeFormat.QR_CODE,
        size,
        size
    )

    val bmp = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565)

    for (x in 0 until size) {
        for (y in 0 until size) {
            bmp.setPixel(x, y, if (bits[x, y]) black else white)
        }
    }

    return bmp
}


@Composable
fun ShowQRScreen(qrText: String) {
    val bitmap = remember { generateQRBitmap(qrText) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "QR Code",
            modifier = Modifier.size(250.dp)
        )

        Text(
            text = qrText,
            modifier = Modifier.padding(top = 16.dp),
            color = Color.Black
        )
    }
}
