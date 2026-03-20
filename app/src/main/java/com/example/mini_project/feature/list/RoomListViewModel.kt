package com.example.mini_project.feature.list

import androidx.lifecycle.ViewModel
import com.example.mini_project.data.RoomRepository
import com.example.mini_project.model.Room
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel cho màn hình danh sách phòng.
 * TODO [Người 2]: Implement logic hiển thị danh sách + xóa phòng
 */
class RoomListViewModel : ViewModel() {

    val rooms: StateFlow<List<Room>> = RoomRepository.roomsFlow

    fun deleteRoom(id: String) {
        // TODO: Gọi RoomRepository.deleteRoom(id)
    }
}
