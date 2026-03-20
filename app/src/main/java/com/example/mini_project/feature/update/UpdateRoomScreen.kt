package com.example.mini_project.feature.update

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Màn hình cập nhật thông tin phòng trọ.
 * TODO [Người 4]: Implement form chỉnh sửa
 *
 * Gợi ý:
 * - Load thông tin phòng hiện tại theo roomId
 * - TextField pre-filled với data hiện tại
 * - Có thể thêm toggle trạng thái: trống / đang thuê
 * - Nếu đang thuê → hiện thêm field tên + SĐT người thuê
 * - Button "Cập nhật" → gọi viewModel.updateRoom(...)
 * - Sau khi cập nhật thành công → gọi onNavigateBack()
 */
@Composable
fun UpdateRoomScreen(
    roomId: String,
    onNavigateBack: () -> Unit,
    viewModel: UpdateRoomViewModel = viewModel()
) {
    // TODO: Implement UI
    // val room = viewModel.getRoomById(roomId)
    // Column { TextField(...), Button(...) }
}
