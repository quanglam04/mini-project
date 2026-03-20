package com.example.mini_project.feature.create

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Màn hình thêm phòng trọ mới.
 * TODO [Người 1]: Implement form nhập liệu
 *
 * Gợi ý:
 * - TextField cho: số phòng, giá thuê, diện tích, mô tả
 * - Validate: số phòng không trống, giá > 0, diện tích > 0
 * - Button "Thêm phòng" → gọi viewModel.createRoom(...)
 * - Sau khi thêm thành công → gọi onNavigateBack()
 */
@Composable
fun CreateRoomScreen(
    onNavigateBack: () -> Unit,
    viewModel: CreateRoomViewModel = viewModel()
) {
    // TODO: Implement UI
    // Column { TextField(...), Button(...) }
    Text(text = "Màn hình tạo mới")
}
