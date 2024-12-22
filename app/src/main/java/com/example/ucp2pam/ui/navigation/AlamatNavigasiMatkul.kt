package com.example.ucp2pam.ui.navigation

interface AlamatNavigasiMatkul {
    val route : String
}
object DestinasiHomeMatkul : AlamatNavigasiMatkul {
    override val route = "HomeMatkul"
}

object DestinasiDetailMatkul : AlamatNavigasiMatkul {
    override val route = "DetailMatkul"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}

object DestinasiUpdateMatkul : AlamatNavigasiMatkul {
    override val route = "UpdateMatkul"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}