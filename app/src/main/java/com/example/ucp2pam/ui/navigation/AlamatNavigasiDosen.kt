package com.example.ucp2pam.ui.navigation

// Interface untuk mendefinisikan alamat navigasi
interface AlamatNavigasiDosen {
    val route: String
}

// Objek yang merepresentasikan destinasi (tujuan) Home Dosen
object DestinasiHomeDosen : AlamatNavigasiDosen {
    override val route = "home_dosen"
}

// Objek yang merepresentasikan destinasi (tujuan) Detail Dosen
object DestinasiDetailDosen : AlamatNavigasiDosen {
    override val route = "detail_dosen"
    const val NIDN = "nidn"
    val routesWithArg = "$route/{$NIDN}"
}