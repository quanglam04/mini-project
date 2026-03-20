package com.example.mini_project.feature.list.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.mini_project.feature.list.RoomFilter
import com.example.mini_project.feature.list.StatusFilter

@Composable
fun FilterSection(
    filter: RoomFilter,
    onFilterChanged: (RoomFilter) -> Unit,
    onClear: () -> Unit
) {
    var minPrice by remember(filter) { mutableStateOf(filter.minPrice?.toLong()?.toString() ?: "") }
    var maxPrice by remember(filter) { mutableStateOf(filter.maxPrice?.toLong()?.toString() ?: "") }
    var minArea by remember(filter) { mutableStateOf(filter.minArea?.toString() ?: "") }
    var maxArea by remember(filter) { mutableStateOf(filter.maxArea?.toString() ?: "") }
    var status by remember(filter) { mutableStateOf(filter.status) }

    var priceError by remember { mutableStateOf<String?>(null) }
    var areaError by remember { mutableStateOf<String?>(null) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Bộ lọc", style = MaterialTheme.typography.titleSmall)

            // Lọc theo giá
            Text("Giá thuê (VNĐ)", style = MaterialTheme.typography.bodySmall)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = minPrice,
                    onValueChange = { minPrice = it; priceError = null },
                    label = { Text("Từ") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    isError = priceError != null
                )
                OutlinedTextField(
                    value = maxPrice,
                    onValueChange = { maxPrice = it; priceError = null },
                    label = { Text("Đến") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    isError = priceError != null
                )
            }
            priceError?.let {
                Text(it, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
            }

            // Lọc theo diện tích
            Text("Diện tích (m²)", style = MaterialTheme.typography.bodySmall)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = minArea,
                    onValueChange = { minArea = it; areaError = null },
                    label = { Text("Từ") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    isError = areaError != null
                )
                OutlinedTextField(
                    value = maxArea,
                    onValueChange = { maxArea = it; areaError = null },
                    label = { Text("Đến") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    isError = areaError != null
                )
            }
            areaError?.let {
                Text(it, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
            }

            // Lọc theo trạng thái
            Text("Trạng thái", style = MaterialTheme.typography.bodySmall)
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                StatusFilter.entries.forEachIndexed { index, statusOption ->
                    SegmentedButton(
                        selected = status == statusOption,
                        onClick = { status = statusOption },
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = StatusFilter.entries.size
                        )
                    ) {
                        Text(statusOption.label, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            // Nút áp dụng + xóa lọc
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(onClick = onClear, modifier = Modifier.weight(1f)) {
                    Text("Xóa lọc")
                }
                Button(
                    onClick = {
                        val minP = minPrice.toDoubleOrNull()
                        val maxP = maxPrice.toDoubleOrNull()
                        val minA = minArea.toDoubleOrNull()
                        val maxA = maxArea.toDoubleOrNull()

                        priceError = when {
                            minPrice.isNotBlank() && minP == null -> "Giá tối thiểu phải là số"
                            maxPrice.isNotBlank() && maxP == null -> "Giá tối đa phải là số"
                            minP != null && minP < 0 -> "Giá không được âm"
                            maxP != null && maxP < 0 -> "Giá không được âm"
                            minP != null && maxP != null && minP > maxP -> "Giá tối thiểu phải nhỏ hơn tối đa"
                            else -> null
                        }
                        areaError = when {
                            minArea.isNotBlank() && minA == null -> "Diện tích tối thiểu phải là số"
                            maxArea.isNotBlank() && maxA == null -> "Diện tích tối đa phải là số"
                            minA != null && minA < 0 -> "Diện tích không được âm"
                            maxA != null && maxA < 0 -> "Diện tích không được âm"
                            minA != null && maxA != null && minA > maxA -> "Diện tích tối thiểu phải nhỏ hơn tối đa"
                            else -> null
                        }

                        if (priceError == null && areaError == null) {
                            onFilterChanged(
                                RoomFilter(
                                    minPrice = minP,
                                    maxPrice = maxP,
                                    minArea = minA,
                                    maxArea = maxA,
                                    status = status
                                )
                            )
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Áp dụng")
                }
            }
        }
    }
}
