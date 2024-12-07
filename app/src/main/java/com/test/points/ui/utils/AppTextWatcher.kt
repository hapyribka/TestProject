package com.test.points.ui.utils

import android.text.TextWatcher

interface AppTextWatcher : TextWatcher {
    override fun beforeTextChanged(
        s: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) {}

    override fun onTextChanged(
        s: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) {}
}
