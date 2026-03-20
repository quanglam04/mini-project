package com.example.mini_project.feature.create

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRoomScreen(
    onNavigateBack: (message: String?) -> Unit,
    viewModel: CreateRoomViewModel = viewModel()
) {
    var roomNumber by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var roomNumberError by remember { mutableStateOf<String?>(null) }
    var priceError by remember { mutableStateOf<String?>(null) }
    var areaError by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Thêm phòng mới") },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack(null) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Quay lại")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = roomNumber,
                onValueChange = {
                    roomNumber = it
                    roomNumberError = null
                },
                label = { Text("Số phòng *") },
                isError = roomNumberError != null,
                supportingText = roomNumberError?.let { msg -> { Text(msg) } },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = price,
                onValueChange = {
                    price = it
                    priceError = null
                },
                label = { Text("Giá thuê (VNĐ/tháng) *") },
                isError = priceError != null,
                supportingText = priceError?.let { msg -> { Text(msg) } },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = area,
                onValueChange = {
                    area = it
                    areaError = null
                },
                label = { Text("Diện tích (m²) *") },
                isError = areaError != null,
                supportingText = areaError?.let { msg -> { Text(msg) } },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Mô tả") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 4
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    // Validate
                    roomNumberError = when {
                        roomNumber.isBlank() -> "Vui lòng nhập số phòng"
                        else -> null
                    }
                    priceError = when {
                        price.isBlank() -> "Vui lòng nhập giá thuê"
                        price.toDoubleOrNull() == null -> "Giá thuê phải là số"
                        price.toDouble() <= 0 -> "Giá thuê phải lớn hơn 0"
                        else -> null
                    }
                    areaError = when {
                        area.isBlank() -> "Vui lòng nhập diện tích"
                        area.toDoubleOrNull() == null -> "Diện tích phải là số"
                        area.toDouble() <= 0 -> "Diện tích phải lớn hơn 0"
                        else -> null
                    }

                    if (roomNumberError == null && priceError == null && areaError == null) {
                        val success = viewModel.createRoom(
                            roomNumber = roomNumber,
                            price = price.toDouble(),
                            area = area.toDouble(),
                            description = description
                        )
                        if (success) onNavigateBack("Đã thêm Phòng $roomNumber")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Thêm phòng")
            }
        }
    }
}