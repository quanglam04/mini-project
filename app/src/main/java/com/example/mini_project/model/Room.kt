package com.example.mini_project.model

import java.util.UUID

data class Room(
    val id: String = UUID.randomUUID().toString(),
    val roomNumber: String = "",        // Số phòng
    val price: Double = 0.0,            // Giá thuê
    val area: Double = 0.0,             // Diện tích (m2)
    val isOccupied: Boolean = false,    // Trạng thái: đang thuê hay trống
    val tenantName: String = "",        // Tên người thuê (nếu có)
    val tenantPhone: String = "",       // SĐT người thuê (nếu có)
    val description: String = ""        // Mô tả thêm
)
