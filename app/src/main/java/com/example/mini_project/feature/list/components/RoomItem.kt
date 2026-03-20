package com.example.mini_project.feature.list.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mini_project.model.Room
import com.example.mini_project.ui.theme.RoomAvailable
import com.example.mini_project.ui.theme.RoomOccupied

@SuppressLint("DefaultLocale")
@Composable
fun RoomItem(
    room: Room,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Phòng ${room.roomNumber}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${String.format("%,.0f", room.price)}đ/tháng • ${room.area}m²",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (room.isOccupied) "Đang thuê: ${room.tenantName}" else "Phòng trống",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (room.isOccupied) RoomOccupied else RoomAvailable
                )
            }
            IconButton(onClick = onDeleteClick) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Xóa phòng",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
