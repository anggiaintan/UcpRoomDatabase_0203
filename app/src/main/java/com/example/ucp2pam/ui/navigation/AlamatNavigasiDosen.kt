package com.example.ucp2pam.ui.navigation

// Interface untuk mendefinisikan alamat navigasi
interface AlamatNavigasiDosen {
    val route: String
}

// Objek yang merepresentasikan destinasi (tujuan) Home Dosen
object DestinasiHomeDosen : AlamatNavigasiDosen {
    override val route = "home_dosen"
}
