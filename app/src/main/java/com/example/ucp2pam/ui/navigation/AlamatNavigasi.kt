package com.example.ucp2pam.ui.navigation

interface Alamatnavigasi {
    val route : String
}

object DestinasiHomeDosen : Alamatnavigasi {
    override val route = "home dosen"
}

object DestinasiHomeMataKuliah : Alamatnavigasi {
    override val route = "home matakuliah"
}

object DestinasiDetailMatakuliah : Alamatnavigasi {
    override  val route = "detail"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}

object DestinasiUpdateMataKuliah : Alamatnavigasi {
    override val route = "update"
    const val KODE = "kode"
    val routeWithArg = "$route/{$KODE}"
}