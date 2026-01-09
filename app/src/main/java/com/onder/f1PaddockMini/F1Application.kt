package com.onder.f1PaddockMini

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class F1Application : Application() {
    // Şimdilik buranın içinin boş olması normal.
    // @HiltAndroidApp notasyonu arka planda tüm
    // Hilt kodlarını (component'leri) otomatik üretecek.
}