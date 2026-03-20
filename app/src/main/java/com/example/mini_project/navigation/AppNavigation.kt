package com.example.mini_project.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mini_project.feature.create.CreateRoomScreen
import com.example.mini_project.feature.list.RoomListScreen
import com.example.mini_project.feature.splash.SplashScreen
import com.example.mini_project.feature.update.UpdateRoomScreen

object Routes {
    const val SPLASH = "splash"
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
        startDestination = Routes.SPLASH
    ) {
        // Splash screen
        composable(Routes.SPLASH) {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(Routes.ROOM_LIST) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        // Màn hình danh sách
        composable(Routes.ROOM_LIST) { backStackEntry ->
            val snackbarMsg by backStackEntry.savedStateHandle
                .getStateFlow<String?>("snackbar_message", null)
                .collectAsState()

            RoomListScreen(
                onNavigateToCreate = {
                    navController.navigate(Routes.CREATE_ROOM)
                },
                onNavigateToUpdate = { roomId ->
                    navController.navigate(Routes.updateRoom(roomId))
                },
                snackbarMessage = snackbarMsg,
                onSnackbarShown = {
                    backStackEntry.savedStateHandle["snackbar_message"] = null
                }
            )
        }

        // Màn hình thêm phòng
        composable(Routes.CREATE_ROOM) {
            CreateRoomScreen(
                onNavigateBack = { message ->
                    if (message != null) {
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("snackbar_message", message)
                    }
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
                onNavigateBack = { message ->
                    if (message != null) {
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("snackbar_message", message)
                    }
                    navController.popBackStack()
                }
            )
        }
    }
}