package ru.practicum.android.diploma.util

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

@SuppressLint("ServiceCast")
fun Fragment.hideKeyboard() {
    val activity = requireActivity()
    val view = activity.currentFocus ?: View(activity)
    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}
