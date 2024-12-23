package com.example.ucp2pam.ui.navigation

interface AlamatNavigasiMatkul {
    val route : String
}
object DestinasiHomeMatkul : AlamatNavigasiMatkul {
    override val route = "homeMatkul"
}

object DestinasiDetailMatkul : AlamatNavigasiMatkul {
    override val route = "detailMatkul"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}

object DestinasiUpdateMatkul : AlamatNavigasiMatkul {
    override val route = "updateMatkul"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}