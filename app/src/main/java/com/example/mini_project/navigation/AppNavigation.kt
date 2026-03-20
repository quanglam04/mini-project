package com.example.mini_project.navigation

import androidx.compose.runtime.Composable
import androidx.navigation .NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mini_project.feature.create.CreateRoomScreen
import com.example.mini_project.feature.list.RoomListScreen
import com.example.mini_project.feature.update.UpdateRoomScreen

/**
 * Navigation chính của app.
 * File này đã khai báo sẵn tất cả route.
 * Các thành viên KHÔNG cần sửa file này — chỉ implement Screen của mình.
 */
object Routes {
    const val ROOM_LIST = "room_list"
    const val CREATE_ROOM = "create_room"
    const val UPDATE_ROOM = "update_room/{roomId}"

    fun updateRoom(roomId: String) = "update_room/$roomId"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.ROOM_LIST
    ) {
        // Màn hình danh sách
        composable(Routes.ROOM_LIST) {
            RoomListScreen(
                onNavigateToCreate = {
                    navController.navigate(Routes.CREATE_ROOM)
                },
                onNavigateToUpdate = { roomId ->
                    navController.navigate(Routes.updateRoom(roomId))
                }
            )
        }

        // Màn hình thêm phòng
        composable(Routes.CREATE_ROOM) {
            CreateRoomScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // Màn hình cập nhật phòng
        composable(
            route = Routes.UPDATE_ROOM,
            arguments = listOf(navArgument("roomId") { type = NavType.StringType })
        ) { backStackEntry ->
            val roomId = backStackEntry.arguments?.getString("roomId") ?: return@composable
            UpdateRoomScreen(
                roomId = roomId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
