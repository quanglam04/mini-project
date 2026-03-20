package com.example.mini_project.feature.list

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Màn hình danh sách phòng trọ.
 * TODO [Người 2 + Người 3]: Implement UI hiển thị danh sách + nút xóa + nút thêm phòng
 *
 * Gợi ý:
 * - Dùng LazyColumn để hiển thị danh sách
 * - Mỗi item hiển thị: số phòng, giá, trạng thái (trống/đang thuê -> trống hiển thị màu xanh, đang thuê hiển thị màu đỏ) + nút cập nhật
 * - Swipe to delete hoặc icon delete(có alert thông báo xóa)
 * - FAB (FloatingActionButton) để chuyển sang màn hình thêm phòng
 * - Click vào item để chuyển sang màn hình cập nhật
 */
@Composable
fun RoomListScreen(
    onNavigateToCreate: () -> Unit,
    onNavigateToUpdate: (roomId: String) -> Unit,
    viewModel: RoomListViewModel = viewModel()
) {
    // TODO: Implement UI
    // val rooms by viewModel.rooms.collectAsState()
    // LazyColumn { items(rooms) { room -> ... } }
}
