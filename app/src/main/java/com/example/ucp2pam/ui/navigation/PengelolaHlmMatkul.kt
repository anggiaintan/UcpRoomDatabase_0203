package com.example.ucp2pam.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2pam.ui.view.matkul.DestinasiInsertMatkul
import com.example.ucp2pam.ui.view.matkul.DetailMatkulView
import com.example.ucp2pam.ui.view.matkul.HomeMatkulView
import com.example.ucp2pam.ui.view.matkul.InsertMatkulView
import com.example.ucp2pam.ui.view.matkul.UpdateMatkulView

@Composable
fun PengelolaHalamanMK(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = DestinasiHomeMatkul.route) {

        //Navigasi Home Mata Kuliah
        composable(
            route = DestinasiHomeMatkul.route
        ) {
            HomeMatkulView(
                onDetailClick = { kode ->
                    navController.navigate("${DestinasiDetailMatkul.route}/$kode")
                    println("PengelolaHalamanMK: kode = $kode")
                },
                onAddMatkul = {
                    navController.navigate(DestinasiInsertMatkul.route)
                },
                modifier = modifier
            )
        }

        //Navigasi Insert Mata Kuliah
        composable(
            route = DestinasiInsertMatkul.route
        ) {
            InsertMatkulView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )
        }

        //Navigasi Detail Mata Kuliah
        composable(
            DestinasiDetailMatkul.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailMatkul.KODE) {
                    type = NavType.StringType
                }
            )
        ) {
            val kode = it.arguments?.getString(DestinasiDetailMatkul.KODE)

            kode?.let { kode ->
                DetailMatkulView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateMatkul.route}/$it")
                    },
                    modifier = modifier,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        //Navigasi Update Mata Kuliah
        composable(
            DestinasiUpdateMatkul.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateMatkul.KODE) {
                    type = NavType.StringType
                }
            )
        ) {
            UpdateMatkulView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }
    }
}