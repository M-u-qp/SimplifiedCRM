package com.example.simplifiedcrm.common.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.getFormattedDate(): String = SimpleDateFormat("EE, dd MMMM yyyy, HH:mm", Locale.getDefault())
    .format(this)