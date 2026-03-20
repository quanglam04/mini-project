package com.example.mini_project.feature.update

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
import com.example.mini_project.model.Room
// code tao boi The Van
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateRoomScreen(
    roomId: String,
    onNavigateBack: (message: String?) -> Unit,
    viewModel: UpdateRoomViewModel = viewModel()
) {
    val room = remember { viewModel.getRoomById(roomId) }

    if (room == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Text("Không tìm thấy phòng")
        }
        return
    }

    var roomNumber by remember { mutableStateOf(room.roomNumber) }
    var price by remember { mutableStateOf(room.price.toLong().toString()) }
    var area by remember { mutableStateOf(room.area.toString()) }
    var description by remember { mutableStateOf(room.description) }
    var isOccupied by remember { mutableStateOf(room.isOccupied) }
    var tenantName by remember { mutableStateOf(room.tenantName) }
    var tenantPhone by remember { mutableStateOf(room.tenantPhone) }

    var roomNumberError by remember { mutableStateOf<String?>(null) }
    var priceError by remember { mutableStateOf<String?>(null) }
    var areaError by remember { mutableStateOf<String?>(null) }
    var tenantNameError by remember { mutableStateOf<String?>(null) }
    var tenantPhoneError by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cập nhật phòng ${room.roomNumber}") },
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
                label = { Text("Tên phòng *") },
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Text(
                    text = "Trạng thái: ${if (isOccupied) "Đang thuê" else "Trống"}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = isOccupied,
                    onCheckedChange = { isOccupied = it }
                )
            }

            // Hiện thêm field người thuê nếu đang thuê
            if (isOccupied) {
                OutlinedTextField(
                    value = tenantName,
                    onValueChange = {
                        tenantName = it
                        tenantNameError = null
                    },
                    label = { Text("Tên người thuê *") },
                    isError = tenantNameError != null,
                    supportingText = tenantNameError?.let { msg -> { Text(msg) } },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = tenantPhone,
                    onValueChange = {
                        tenantPhone = it
                        tenantPhoneError = null
                    },
                    label = { Text("SĐT người thuê *") },
                    isError = tenantPhoneError != null,
                    supportingText = tenantPhoneError?.let { msg -> { Text(msg) } },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
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
                        // Validate người thuê nếu đang thuê
                        if (isOccupied) {
                            tenantNameError = if (tenantName.isBlank()) "Vui lòng nhập tên người thuê" else null
                            tenantPhoneError = when {
                                tenantPhone.isBlank() -> "Vui lòng nhập SĐT người thuê"
                                !tenantPhone.all { it.isDigit() } -> "SĐT chỉ được chứa số"
                                tenantPhone.length < 9 -> "SĐT phải có ít nhất 9 số"
                                tenantPhone.length > 11 -> "SĐT không được quá 11 số"
                                else -> null
                            }
                        } else {
                            tenantNameError = null
                            tenantPhoneError = null
                        }

                        if (tenantNameError == null && tenantPhoneError == null) {
                            val updatedRoom = room.copy(
                                roomNumber = roomNumber,
                                price = price.toDouble(),
                                area = area.toDouble(),
                                description = description,
                                isOccupied = isOccupied,
                                tenantName = if (isOccupied) tenantName else "",
                                tenantPhone = if (isOccupied) tenantPhone else ""
                            )
                            val success = viewModel.updateRoom(updatedRoom)
                            if (success) onNavigateBack("Đã cập nhật Phòng $roomNumber")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cập nhật")
            }
        }
    }
}