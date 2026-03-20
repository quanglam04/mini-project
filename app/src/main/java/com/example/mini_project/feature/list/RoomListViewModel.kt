package com.example.mini_project.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mini_project.data.RoomRepository
import com.example.mini_project.model.Room
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class StatusFilter(val label: String) {
    ALL("Tất cả"),
    OCCUPIED("Đang thuê"),
    AVAILABLE("Phòng trống")
}

data class RoomFilter(
    val minPrice: Double? = null,
    val maxPrice: Double? = null,
    val minArea: Double? = null,
    val maxArea: Double? = null,
    val status: StatusFilter = StatusFilter.ALL
)

data class RoomListUiState(
    val rooms: List<Room> = emptyList(),
    val filter: RoomFilter = RoomFilter(),
    val isFiltering: Boolean = false
)

sealed class RoomListUiEvent {
    data class ShowSnackbar(val message: String) : RoomListUiEvent()
}

class RoomListViewModel : ViewModel() {

    private val _filter = MutableStateFlow(RoomFilter())

    val uiState: StateFlow<RoomListUiState> = combine(
        RoomRepository.roomsFlow,
        _filter
    ) { allRooms, filter ->
        RoomListUiState(
            rooms = applyFilter(allRooms, filter),
            filter = filter,
            isFiltering = filter != RoomFilter()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = RoomListUiState()
    )

    private val _uiEvent = Channel<RoomListUiEvent>(Channel.BUFFERED)
    val uiEvent: Flow<RoomListUiEvent> = _uiEvent.receiveAsFlow()

    fun deleteRoom(room: Room) {
        RoomRepository.deleteRoom(room.id)
        viewModelScope.launch {
            _uiEvent.send(RoomListUiEvent.ShowSnackbar("Đã xóa Phòng ${room.roomNumber}"))
        }
    }

    fun updateFilter(newFilter: RoomFilter) {
        _filter.value = newFilter
    }

    fun clearFilter() {
        _filter.value = RoomFilter()
    }

    private fun applyFilter(rooms: List<Room>, filter: RoomFilter): List<Room> =
        rooms.filter { room ->
            val priceOk =
                (filter.minPrice == null || room.price >= filter.minPrice) &&
                        (filter.maxPrice == null || room.price <= filter.maxPrice)
            val areaOk =
                (filter.minArea == null || room.area >= filter.minArea) &&
                        (filter.maxArea == null || room.area <= filter.maxArea)
            val statusOk = when (filter.status) {
                StatusFilter.ALL      -> true
                StatusFilter.OCCUPIED -> room.isOccupied
                StatusFilter.AVAILABLE -> !room.isOccupied
            }
            priceOk && areaOk && statusOk
        }
}

