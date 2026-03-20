package com.example.mini_project.data

import com.example.mini_project.model.Room
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Repository quản lý dữ liệu phòng trọ.
 * Dùng List lưu tạm trong memory (không dùng database).
 * Singleton pattern - cả app dùng chung 1 instance.
 */
object RoomRepository {

    // Dữ liệu lưu tạm bằng MutableList
    private val _rooms = mutableListOf<Room>()

    // StateFlow để UI tự động cập nhật khi data thay đổi
    private val _roomsFlow = MutableStateFlow<List<Room>>(emptyList())
    val roomsFlow: StateFlow<List<Room>> = _roomsFlow.asStateFlow()

    init {
        // Dữ liệu mẫu để test
        _rooms.addAll(
            listOf(
                Room(
                    roomNumber = "101",
                    price = 3000000.0,
                    area = 20.0,
                    isOccupied = true,
                    tenantName = "Nguyễn Văn A",
                    tenantPhone = "0901234567",
                    description = "Phòng có máy lạnh, ban công"
                ),
                Room(
                    roomNumber = "102",
                    price = 2500000.0,
                    area = 18.0,
                    isOccupied = false,
                    description = "Phòng trống, có gác lửng"
                ),
                Room(
                    roomNumber = "103",
                    price = 3500000.0,
                    area = 25.0,
                    isOccupied = true,
                    tenantName = "Trần Thị B",
                    tenantPhone = "0912345678",
                    description = "Phòng rộng, có bếp riêng"
                ),Room(
                    roomNumber = "105",
                    price = 3000000.0,
                    area = 20.0,
                    isOccupied = true,
                    tenantName = "Nguyễn Văn A",
                    tenantPhone = "0901234567",
                    description = "Phòng có máy lạnh, ban công"
                ),Room(
                    roomNumber = "106",
                    price = 3000000.0,
                    area = 20.0,
                    isOccupied = true,
                    tenantName = "Nguyễn Văn A",
                    tenantPhone = "0901234567",
                    description = "Phòng có máy lạnh, ban công"
                ),Room(
                    roomNumber = "107",
                    price = 3000000.0,
                    area = 20.0,
                    isOccupied = true,
                    tenantName = "Nguyễn Văn A",
                    tenantPhone = "0901234567",
                    description = "Phòng có máy lạnh, ban công"
                ),Room(
                    roomNumber = "108",
                    price = 3000000.0,
                    area = 20.0,
                    isOccupied = true,
                    tenantName = "Nguyễn Văn A",
                    tenantPhone = "0901234567",
                    description = "Phòng có máy lạnh, ban công"
                ),Room(
                    roomNumber = "109",
                    price = 3000000.0,
                    area = 20.0,
                    isOccupied = true,
                    tenantName = "Nguyễn Văn A",
                    tenantPhone = "0901234567",
                    description = "Phòng có máy lạnh, ban công"
                ),Room(
                    roomNumber = "110",
                    price = 3000000.0,
                    area = 20.0,
                    isOccupied = true,
                    tenantName = "Nguyễn Văn A",
                    tenantPhone = "0901234567",
                    description = "Phòng có máy lạnh, ban công"
                ),Room(
                    roomNumber = "111",
                    price = 3000000.0,
                    area = 20.0,
                    isOccupied = true,
                    tenantName = "Nguyễn Văn A",
                    tenantPhone = "0901234567",
                    description = "Phòng có máy lạnh, ban công"
                ),Room(
                    roomNumber = "112",
                    price = 3000000.0,
                    area = 20.0,
                    isOccupied = true,
                    tenantName = "Nguyễn Văn A",
                    tenantPhone = "0901234567",
                    description = "Phòng có máy lạnh, ban công"
                ),
            )
        )
        emitUpdate()
    }

    fun getAllRooms(): List<Room> = _rooms.toList()

    fun getRoomById(id: String): Room? = _rooms.find { it.id == id }

    fun addRoom(room: Room) {
        _rooms.add(room)
        emitUpdate()
    }

    fun updateRoom(updatedRoom: Room) {
        val index = _rooms.indexOfFirst { it.id == updatedRoom.id }
        if (index != -1) {
            _rooms[index] = updatedRoom
            emitUpdate()
        }
    }

    fun deleteRoom(id: String) {
        _rooms.removeAll { it.id == id }
        emitUpdate()
    }

    private fun emitUpdate() {
        _roomsFlow.value = _rooms.toList()
    }
}