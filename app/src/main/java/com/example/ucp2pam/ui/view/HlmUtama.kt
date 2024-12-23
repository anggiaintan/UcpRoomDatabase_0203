package com.example.ucp2pam.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ucp2pam.R

@Composable
fun HlmUtama(navController: NavController, modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(color = Color(0xFF8B4513))
            ) {
                Row(
                    modifier = Modifier
                        .padding(30.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(110.dp),
                        painter = painterResource(id = R.drawable.umyhd),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.padding(start = 20.dp))
                    Column {
                        Text(
                            text = "Universitas Muhammadiyah Yogyakarta",
                            color = Color.White,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Justify
                        )
                        Text(
                            text = "Teknologi Informasi",
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = Color.White.copy(alpha = 0.9f),
                                shape = RoundedCornerShape(
                                    topEnd = 18.dp,
                                    topStart = 18.dp
                                )
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            // Judul yang diperbarui
                            Text(
                                text = "Halo! Selamat Datang di Teknologi Informasi",
                                color = Color.Black,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            // Subtitle
                            Text(
                                text = "Silahkan pilih menu berikut",
                                color = Color.Black.copy(alpha = 0.7f),
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(bottom = 24.dp)
                            )

                            // Tombol Dosen dengan ikon
                            Button(
                                onClick = {
                                    navController.navigate("Dosen")
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF9C9F5B),
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .height(70.dp)
                                    .fillMaxWidth()
                                    .shadow(4.dp, RoundedCornerShape(12.dp)),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Person, // Menggunakan ikon Person
                                        contentDescription = "Ikon Dosen",
                                        tint = Color.White,
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                    Text(
                                        text = "Dosen",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.weight(1f),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }

                            // Tombol Matakuliah dengan ikon
                            Button(
                                onClick = {
                                    navController.navigate("Matkul")
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF9C9F5B),
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .height(70.dp)
                                    .fillMaxWidth()
                                    .shadow(4.dp, RoundedCornerShape(12.dp)),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AddCircle, // Menggunakan ikon Book
                                        contentDescription = "Ikon Matakuliah",
                                        tint = Color.White,
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                    Text(
                                        text = "Mata Kuliah",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.weight(1f),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}
