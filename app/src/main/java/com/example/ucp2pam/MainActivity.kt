package com.example.ucp2pam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ucp2pam.ui.navigation.PengelolaHlmDosen
import com.example.ucp2pam.ui.navigation.PengelolaHlmMatkul
import com.example.ucp2pam.ui.theme.UCP2PAMTheme
import com.example.ucp2pam.ui.view.HalamanUtama

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UCP2PAMTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "Utama",
                        modifier = Modifier.padding(innerPadding) //
                    ) {
                        composable("Utama") {
                            HalamanUtama(navController)
                        }
                        composable("Dosen") {
                            PengelolaHlmDosen()
                        }
                        composable("MK") {
                            PengelolaHlmMatkul()
                        }
                    }
                }
            }
        }
    }
}