package com.example.mini_project.feature.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mini_project.feature.list.components.FilterSection
import com.example.mini_project.feature.list.components.RoomItem
import com.example.mini_project.model.Room

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomListScreen(
    onNavigateToCreate: () -> Unit,
    onNavigateToUpdate: (roomId: String) -> Unit,
    snackbarMessage: String? = null,
    onSnackbarShown: () -> Unit = {},
    viewModel: RoomListViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var roomToDelete by remember { mutableStateOf<Room?>(null) }
    var showFilter by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    // Snackbar từ Create/Update screen (qua savedStateHandle)
    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let {
            snackbarHostState.showSnackbar(it)
            onSnackbarShown()
        }
    }

    // Snackbar nội bộ (xóa phòng)
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is RoomListUiEvent.ShowSnackbar ->
                    snackbarHostState.showSnackbar(event.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Quản lý phòng trọ") },
                actions = {
                    if (uiState.isFiltering) {
                        IconButton(onClick = { viewModel.clearFilter() }) {
                            Icon(Icons.Default.Clear, contentDescription = "Xóa bộ lọc")
                        }
                    }
                    IconButton(onClick = { showFilter = !showFilter }) {
                        Icon(
                            Icons.Default.FilterList,
                            contentDescription = "Bộ lọc",
                            tint = if (uiState.isFiltering) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreate) {
                Icon(Icons.Default.Add, contentDescription = "Thêm phòng")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Bộ lọc (ẩn/hiện)
            AnimatedVisibility(visible = showFilter) {
                FilterSection(
                    filter = uiState.filter,
                    onFilterChanged = viewModel::updateFilter,
                    onClear = viewModel::clearFilter
                )
            }

            // Số kết quả khi đang lọc
            if (uiState.isFiltering) {
                Text(
                    text = "Tìm thấy ${uiState.rooms.size} phòng",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }

            // Danh sách phòng
            if (uiState.rooms.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (uiState.isFiltering) "Không có phòng phù hợp bộ lọc."
                        else "Chưa có phòng nào.\nBấm + để thêm phòng mới.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.rooms, key = { it.id }) { room ->
                        RoomItem(
                            room = room,
                            onClick = { onNavigateToUpdate(room.id) },
                            onDeleteClick = { roomToDelete = room }
                        )
                    }
                }
            }
        }
    }

    // Dialog xác nhận xóa
    roomToDelete?.let { room ->
        AlertDialog(
            onDismissRequest = { roomToDelete = null },
            title = { Text("Xác nhận xóa") },
            text = { Text("Bạn có chắc muốn xóa Phòng ${room.roomNumber}?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteRoom(room)
                        roomToDelete = null
                    }
                ) {
                    Text("Xóa", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { roomToDelete = null }) {
                    Text("Hủy")
                }
            }
        )
    }
}