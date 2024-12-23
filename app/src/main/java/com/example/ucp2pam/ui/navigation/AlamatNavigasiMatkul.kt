package com.example.ucp2pam.ui.navigation


interface AlamatNavigasiMK {
    val route: String
}

object DestinasiHomeMatkul : AlamatNavigasiMK {
    override val route = "homeMatkul"
}

object DestinasiDetailMatkul : AlamatNavigasiMK {
    override val route = "detailMatkul"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}

object DestinasiUpdateMatkul : AlamatNavigasiMK {
    override val route = "updateMatkul"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}