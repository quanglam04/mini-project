package com.example.mini_project.feature.update

import androidx.lifecycle.ViewModel
import com.example.mini_project.data.RoomRepository
import com.example.mini_project.model.Room

/**
 * ViewModel cho màn hình cập nhật phòng.
 * TODO [Người 4]: Implement logic load phòng + cập nhật
 */
class UpdateRoomViewModel : ViewModel() {

    fun getRoomById(id: String): Room? {
        // TODO: Gọi RoomRepository.getRoomById(id)
        return null
    }

    fun updateRoom(room: Room) {
        // TODO: Gọi RoomRepository.updateRoom(room)
    }
}
