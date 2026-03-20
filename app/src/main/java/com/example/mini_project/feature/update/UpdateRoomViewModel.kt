package com.example.mini_project.feature.update

import androidx.lifecycle.ViewModel
import com.example.mini_project.data.RoomRepository
import com.example.mini_project.model.Room

class UpdateRoomViewModel : ViewModel() {

    fun getRoomById(id: String): Room? {
        return RoomRepository.getRoomById(id)
    }

    fun updateRoom(room: Room): Boolean {
        if (room.roomNumber.isBlank() || room.price <= 0 || room.area <= 0) return false

        RoomRepository.updateRoom(room)
        return true
    }
}
