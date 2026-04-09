//package com.onder.f1PaddockMini.core.common
//
//object Constants {
//    // Android Emulator kullanıyorsan localhost yerine 10.0.2.2 kullanılır.
//    const val BASE_URL = "http://10.0.2.2:8000/"
//}

package com.onder.f1PaddockMini.core.common

import android.os.Build

object Constants {

    val BASE_URL: String
        get() = if (Build.FINGERPRINT.contains("generic")) {
            // Emulator
            "http://10.0.2.2:8000/"
        } else {
            // Fiziksel cihaz
            "http://192.168.1.3:8000/"
        }
}