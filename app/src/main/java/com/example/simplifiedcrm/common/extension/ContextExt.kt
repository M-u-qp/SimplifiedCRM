package com.example.simplifiedcrm.common.extension

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.dialPhoneNumber(phoneNumber: String) {
    Intent(Intent.ACTION_DIAL).also {
        it.data = Uri.parse("tel:$phoneNumber")
        if (it.resolveActivity(packageManager) != null) {
            startActivity(it)
        }
    }
}

fun Context.sendToEmail(email: String) {
    Intent(Intent.ACTION_SENDTO).also {
        it.data = Uri.parse("mailto:$email")
        if (it.resolveActivity(packageManager) != null) {
            startActivity(it)
        }
    }
}