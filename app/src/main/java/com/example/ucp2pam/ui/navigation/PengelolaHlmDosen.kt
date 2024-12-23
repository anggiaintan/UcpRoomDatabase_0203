package com.example.ucp2pam.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ucp2pam.ui.view.dosen.DestinasiInsert
import com.example.ucp2pam.ui.view.dosen.HomeDosenView
import com.example.ucp2pam.ui.view.dosen.InsertDosenView

@Composable
fun PengelolaHalamanDosen(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = DestinasiHomeDosen.route) {

        composable(
            route = DestinasiHomeDosen.route
        ) {
            HomeDosenView(
                onAddDosen = {
                    navController.navigate(DestinasiInsert.route)
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiInsert.route
        ) {
            InsertDosenView(
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