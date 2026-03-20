package com.example.mini_project.feature.create

import androidx.lifecycle.ViewModel


/**
 * ViewModel cho màn hình thêm phòng.
 * TODO [Người 3]: Implement logic thêm phòng mới
 */
class CreateRoomViewModel : ViewModel() {

    fun createRoom(
        roomNumber: String,
        price: Double,
        area: Double,
        description: String
    ) {
        // TODO: Tạo Room mới và gọi RoomRepository.addRoom(...)
    }
}
