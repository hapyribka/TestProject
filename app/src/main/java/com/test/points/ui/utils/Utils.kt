package com.test.points.ui.utils

import android.content.Context

fun buildErrorMessage(
    context: Context,
    textResId: Int,
    message: String
): String? {
    val firstPart = if (textResId > 0) context.getString(textResId) else ""
    return "$firstPart $message".trim()
}
