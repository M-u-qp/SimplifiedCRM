package com.example.simplifiedcrm.common.extension

import androidx.compose.ui.graphics.Color
import com.example.simplifiedcrm.ui.screens.component.TaskByStatusSortOrder
import com.example.simplifiedcrm.ui.theme.dark_error

fun String.getColors(
    activeColors: Triple<Color, Color, Color>,
    expiredColors: Triple<Color, Color, Color>,
    doneColors: Triple<Color, Color, Color>,
): Triple<Color, Color, Color> {
    return when (this) {
        TaskByStatusSortOrder.ACTIVE.name -> activeColors
        TaskByStatusSortOrder.EXPIRED.name -> expiredColors
        TaskByStatusSortOrder.DONE.name -> doneColors
        else -> {
            Triple(dark_error, dark_error, dark_error)
        }
    }
}