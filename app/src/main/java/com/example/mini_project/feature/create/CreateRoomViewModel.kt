package com.example.mini_project.feature.create

import androidx.lifecycle.ViewModel
import com.example.mini_project.data.RoomRepository
import com.example.mini_project.model.Room

class CreateRoomViewModel : ViewModel() {

    fun createRoom(
        roomNumber: String,
        price: Double,
        area: Double,
        description: String
    ): Boolean {
        if (roomNumber.isBlank() || price <= 0 || area <= 0) return false

        val room = Room(
            roomNumber = roomNumber,
            price = price,
            area = area,
            isOccupied = false,
            description = description
        )
        RoomRepository.addRoom(room)
        return true
    }
}