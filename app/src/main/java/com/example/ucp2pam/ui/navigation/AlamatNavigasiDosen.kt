package com.example.ucp2pam.ui.navigation

// Interface untuk mendefinisikan alamat navigasi
interface AlamatNavigasi {
    val route: String
}

// Objek yang merepresentasikan destinasi (tujuan) Home Dosen
object DestinasiHomeDosen : AlamatNavigasi {
    override val route = "home_dosen"
}
